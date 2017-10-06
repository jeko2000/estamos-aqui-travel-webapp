(ns eat.layout
  (:require [stasis.core :refer [merge-page-sources slurp-directory slurp-resources]]
            [eat.web.index :refer [index-page]]
            [eat.web.post :refer [post-pages]]
            [eat.web.tag :refer [tag-pages]]))

(defn get-pages [posts]
  (merge-page-sources
   {#_:assets #_(slurp-resources "public" #"^.*?\.(css|png|jpg)")
    :index (index-page posts)
    :tags (tag-pages posts)
    :posts (post-pages posts)}))



