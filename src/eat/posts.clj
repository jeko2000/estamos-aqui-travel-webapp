(ns eat.posts
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [me.raynes.cegdown :as md]))

;;TODO: Add this to config
(def posts-prefix "/post")
(def pages-prefix "/")
(def default-author "Shell")
(def number-of-most-recent-posts 5)


(defn build-path [& parts]
  "Return folder-like uri from path PARTS"
  (as-> parts $
       (str/join "/" $)
       (str/replace $ #"/{2,}" "/")))

(defn get-resource [resource]
  "Return file object from given RESOURCE"
  (-> resource io/resource io/file))

(defn read-resource [resource]
  "Return string representing contents from RESOURCE. If RESOURCE cannot be found, return empty string"
  (if-let [res (get-resource resource)]
    (slurp res)
    ""))

(defn get-contents [root]
  "Return a list of files objects associated with those under the given ROOT. Excludes root file object from result"
  (if-let [root (get-resource root)]
    (->> root
         file-seq
         rest
         (remove #(str/starts-with? (.getName %) ".")))
    '()))

(defn- parse-post-date [file-name]
  "Return a string representing a post's date. This assumes post files are named following yyyy-MM-dd-post-title.md"
  (.substring file-name 0 10))

(defn parse-url [file-name prefix]
  "Return url for a given FILE-NAME by prefixing it with PREFIX and appending a '/'"
  ;;TODO. It'd be great to make this more robust to allow different options for building urls from file names.
  (str (build-path prefix (str/replace file-name #"\..*$" "")) "/"))

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
      :content (md/to-html (read-file-content-without-metadata post))
      :url  (parse-url post-name posts-prefix)
      :date (parse-post-date post-name)
      :preview "This is a sample preview.
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Nulla nec tortor. Donec id elit quis purus consectetur consequat."
      :preview-img "img/900x300.png"}
     (read-file-metadata post))))

(defn build-posts [posts-root]
  (sort-by :date (mapv build-post-map (get-contents posts-root))))
