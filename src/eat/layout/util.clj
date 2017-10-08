(ns eat.layout.util
  (:require [eat.util :refer [build-path]]))

(defn contains-tag? [tag post]
  (if-let [tags (:tags post)]
    (contains? tags tag)))

(defn get-all-tags [posts]
  (->> posts
       (map :tags)
       (apply clojure.set/union)
       (remove nil?)))

(defn urlize-tag [tag tags-output-prefix]
  (as-> tag $
      (clojure.string/replace $ #"\s+" "-")
      (build-path tags-output-prefix $)))
