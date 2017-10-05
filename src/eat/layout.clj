(ns eat.layout
  (:require [stasis.core :refer [merge-page-sources slurp-directory slurp-resources]]
            [eat.web.index :refer [index]]
            [eat.web.post :refer [post]]))

(defn index-page [posts]
  {"/" (index posts)})

(defn post-page [[post-map next] posts]
  [(:url post-map)
   (post post-map next posts)])

(defn post-pages [posts]
  (->> posts
       (partition-all 2 1)
       (map #(post-page % posts))
       (into {})))

(defn get-pages [posts]
  (merge-page-sources
   {#_:assets #_(slurp-resources "public" #"^.*?\.(css|png|jpg)")
    :index (index-page posts)
    :posts (post-pages posts)}))



