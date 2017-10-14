(ns eat.layout.user.tag
  (:require [eat.layout.user.base :refer [base previews]]))

 (defn tag-page [layout-config target-tag posts-with-tag]
  (base layout-config
        {:title (str "Posts under tag " target-tag)
         :content (previews layout-config posts-with-tag (str "Posts with tag " target-tag))
         :posts posts-with-tag}))
