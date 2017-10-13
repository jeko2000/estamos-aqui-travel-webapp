(ns eat.layout.user.tag
  (:require [eat.layout.user.base :refer [base previews]]
            [eat.db.core :refer [contains-tag?]]))

 (defn tag-page [layout-config target-tag posts]
  (let [posts-with-tag (filter #(contains-tag? target-tag %) posts)]
    (base layout-config
          {:title (str "Posts under tag " target-tag)
           :content (previews layout-config posts-with-tag (str "Posts with tag " target-tag))
           :posts posts})))
