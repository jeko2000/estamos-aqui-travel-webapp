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
  (-> resource io/resource io/file))

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

