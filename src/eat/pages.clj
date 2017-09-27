(ns eat.pages
  (:require [stasis.core :refer [merge-page-sources]]
            [eat.pages.index :refer [get-index-page]]
            [eat.pages.posts :refer [get-post-pages]]))

(defn create-pages [content]
  (merge-page-sources
   {:general-pages (get-index-page content)
    :posts (get-post-pages content)}))

