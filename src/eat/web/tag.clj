(ns eat.web.tag
  (:require [eat.web.base :refer [base previews]]
            [eat.web.util :refer [build-path contains-tag? get-all-tags urlize-tag]]))


(defn tag [target-tag posts]
  (let [posts-with-tag (filter #(contains-tag? target-tag %) posts)]
    (base {:title (str "Posts under tag " target-tag)
           :content (previews posts-with-tag (str "Posts with tag " target-tag))})))

(defn tag-page [target-tag posts]
  [(urlize-tag target-tag)
   (fn [req]
     (tag target-tag posts))])


(defn tag-pages [posts]
  (let [all-tags (get-all-tags posts)]
    (->> all-tags
         (map #(tag-page % posts))
         (into {}))))

