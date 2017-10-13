(ns eat.db.util
  (:require [eat.util :refer [build-path get-resource get-contents]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [me.raynes.cegdown :as md]))

;;TODO: Add this to config
(def posts-prefix "/post")

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

(defn read-file-content-without-metadata [file]
  "Return contents from FILE as a string without the file metadata on top.
   If no metadata is found, then it returns the entire FILE's contents."
  (let [full-page (slurp file)
        delim "\n\n\n"
        end-of-meta-data (str/index-of full-page delim)]
    (if end-of-meta-data
      (subs full-page (+ end-of-meta-data (count delim)))
      full-page)))

(defn build-post-map [post]
  (let [post-name (.getName post)
        file-contents-no-meta (read-file-content-without-metadata post)]
    (merge
     {:resource-name (str/replace (str post) #"^.*?resources/" "")
      :content (md/to-html file-contents-no-meta)
      :url  (parse-url post-name posts-prefix)
      :md file-contents-no-meta}
     (read-file-metadata post))))

(defn build-posts [posts-root]
  (sort-by :date (map build-post-map (get-contents posts-root))))

(defn spit-file! [path file-name contents]
  (if-let [res (get-resource path)]
    (spit (build-path (str res) file-name) contents :encoding "utf-8")))

(defn copy-file! [file path file-name]
  (if-let [res (get-resource path)]
    (io/copy file (io/file (build-path (str res) file-name)))))
