(ns eat.util
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn build-path [& parts]
  "Return folder-like uri from path PARTS"
  (as-> parts $
       (str/join "/" $)
       (str/replace $ #"/{2,}" "/")))

(defn get-resource [resource]
  "Return file object from given RESOURCE"
  (-> resource io/resource))

(defn read-resource [resource]
  "Return string representing contents from RESOURCE. If RESOURCE cannot be found, return nil"
  (if-let [res (get-resource resource)]
    (slurp res)))

(defn get-contents [root]
  "Return a list of files objects associated with those under the given ROOT. Excludes root file object from result"
  (if-let [root (get-resource root)]
    (->> root
         file-seq
         rest
         (remove #(str/starts-with? (.getName %) ".")))
    '()))

(defn title->url [title]
    (-> title
      (str/lower-case)
      (str/replace #"\s+" "-")
      (str/replace #"á" "a")
      (str/replace #"é" "e")
      (str/replace #"í" "i")
      (str/replace #"ó" "o")
      (str/replace #"ú" "u")
      (str/replace #"ñ" "n")      
      (str/replace #"[^a-z0-9-]" "")
      (->> (str "/posts/"))))

(defn tag->uri [tag tags-output-prefix]
  (as-> tag $
      (clojure.string/replace $ #"\s+" "-")
      (build-path tags-output-prefix $)))

(defn string->keyword [s]
  (-> s
      (str/replace #"(_|\.)" "-")
      str/lower-case
      keyword))

(defn copy-file! [file path file-name]
  (io/copy file (io/file (build-path path file-name))))
