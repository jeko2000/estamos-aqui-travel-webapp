(ns eat.db.util
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [eat.util :refer [build-path get-resource read-resource get-contents]]
            [me.raynes.cegdown :as md]))

;;TODO: Add this to config
(def posts-prefix "/post")
(def pages-prefix "/")
(def default-author "Shell")
(def number-of-most-recent-posts 5)

(defn parse-url [file-name prefix]
  "Return url for a given FILE-NAME by prefixing it with PREFIX"
  ;;TODO. It'd be great to make this more robust to allow different options for building urls from file names.
  (str (build-path prefix (str/replace file-name #"\..*$" ""))))

(defn read-file-metadata [file]
  "Attempt to return clojure structure atop given FILE"
  (with-open [rdr (java.io.PushbackReader. (io/reader file))] ;read requires casting to PushBackReader
    (try
      (read rdr)
      (catch Exception e
        (throw (AssertionError. (str "No readable metadata on top of file " (.getName file) "!")))))))

(defn process-file-metadata [metadata]
  (if (and (:tags metadata) (not (set? metadata)))
    (update metadata :tags (fn [[s]] (set (clojure.string/split s #"\s+"))))
    metadata))

(defn read-file-content-without-metadata [file]
  "Return contents from FILE as a string without the file metadata on top.
   If no metadata is found, then it returns the entire FILE's contents."  
  (let [full-page (slurp file)
        end-of-meta-data (str/index-of full-page "}")]
    (if end-of-meta-data
      (str/replace full-page #"^(.|\s)*?\}" "")
      full-page)))

(defn build-post-map [post]
  (let [post-name (.getName post)
        file-contents-no-meta (read-file-content-without-metadata post)]
    (merge
     {:resource-name (str/replace (str post) #"^.*?resources/" "")
      :content (md/to-html file-contents-no-meta)
      :url  (parse-url post-name posts-prefix)
      :md file-contents-no-meta}
     ((comp process-file-metadata read-file-metadata) post))))

(defn build-posts [posts-root]
  (sort-by :date (map build-post-map (get-contents posts-root))))

(defn spit-file! [path file-name contents]
  (if-let [res (get-resource path)]
(spit (build-path (str res) file-name) contents :encoding "utf-8")))

