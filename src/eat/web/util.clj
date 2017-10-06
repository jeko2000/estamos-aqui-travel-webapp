(ns eat.web.util
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

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

(def tags-output-prefix "/tags") ;;Add to config

(defn contains-tag? [tag post]
  (if-let [tags (:tags post)]
    (contains? tags tag)))

(defn get-all-tags [posts]
  (->> posts
       (map :tags)
       (apply clojure.set/union)
       (remove nil?)))

(defn urlize-tag [tag]
  (as-> tag $
      (clojure.string/replace $ #"\s+" "-")
      (build-path tags-output-prefix $ "")))
