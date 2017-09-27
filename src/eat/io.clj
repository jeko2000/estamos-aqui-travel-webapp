(ns eat.io
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

;;TODO: Add this to config
(def posts-prefix "/post")
(def pages-prefix "/")
(def default-author "Shell")
(def number-of-most-recent-posts 5)

;;TODO Figure out tag management
;;TODO Handle preview images. Compute blurb from article

(defn build-path [& parts]
  (str/join "/" parts))

(defn get-resource [resource]
  "Return file object from given RESOURCE"
  (-> resource io/resource io/file))

(defn read-resource [resource]
  "Return string representing contents from RESOURCE. If RESOURCE cannot be found, return empty string"
  (if-let [res (get-resource resource)]
    (slurp res)
    ""))

(defn read-edn-resource [edn-resource]
  "Return edn resource"
  (read-string (read-resource edn-resource)))

(defn- get-contents [root]
  "Return a list of files objects associated with those under the given ROOT. Excludes root file object from result"
  (if-let [root (get-resource root)]
    (->> root
         file-seq
         rest
         (remove #(str/starts-with? (.getName %) ".")))))

(defn- get-content-names [root]
  "Return a list of files names associated with those under the given ROOT. Excludes root file object from result"
  (if-let [file-objects (get-contents root)]
    (map #(.getName %) file-objects)))

#_(defn- parse-raw-title [^String name]
  (.substring name 11))

(defn- parse-post-date [file-name]
  "Return a string representing a post's date. This assumes post files are named following yyyy-MM-dd-post-title.md"
  (.substring file-name 0 10))

#_(defn- parse-title [raw-title]
  (str/replace
   (str/replace raw-title "-" " ")
   #"\..*$" ""))

(defn parse-url [file-name prefix]
  "Return url for a given FILE-NAME"
  ;;TODO. It'd be great to make this more robust to allow different options for building urls from file names.
  (build-path prefix (str/replace file-name #"\..*$" "")))

(defn read-file-metadata [file]
  "Attempt to return clojure structure atop given FILE"
  (with-open [rdr (java.io.PushbackReader. (io/reader file))] ;read requires casting to PushBackReader
    (try
      (read rdr)
      (catch Exception e
        (throw (AssertionError. (str "No readable metadata on top of file " (.getName file) "!")))))))

(defn read-file-content-without-metadata [file]
  "Return contents from FILE as a string without the file metadata on top.
   If no metadata is found, then it returns the entire FILE's contents."  
  (let [full-page (slurp file)
        end-of-meta-data (str/index-of full-page "}")]
    (if end-of-meta-data
      (str/replace full-page #"^(.|\s)*?\}" "")
      full-page)))

(defn build-post-map [post]
  (let [post-name (.getName post)]
    (merge
     {:resource-name (str/replace (str post) #"^.*?resources/" "")
      :content (read-file-content-without-metadata post)
      :url (parse-url post-name posts-prefix)
      :date (parse-post-date post-name)}
     (read-file-metadata post))))

(defn build-page-map [page]
  (let [page-name (.getName page)]
     {:resource-name (str/replace (str page) #"^.*?resources/" "")
      :content (slurp page)
      :url (parse-url page-name pages-prefix)}))

(defn build-posts [posts-root]
  (sort-by :date (mapv build-post-map (get-contents posts-root))))
(defn build-pages [pages-root]
  (sort-by :date  (mapv build-page-map (get-contents pages-root))))

