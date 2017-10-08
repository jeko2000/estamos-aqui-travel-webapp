(ns eat.layout.tag
  (:require [eat.layout.base :refer [base previews]]
            [eat.util :refer [build-path]]
            [eat.layout.util :refer [contains-tag? get-all-tags urlize-tag]]))

 (defn tag-page [layout-config target-tag posts]
  (let [posts-with-tag (filter #(contains-tag? target-tag %) posts)]
    (base layout-config
          {:title (str "Posts under tag " target-tag)
           :content (previews layout-config posts-with-tag (str "Posts with tag " target-tag))
           :posts posts})))
