(ns eat.content
  (:require [eat.io :refer [build-pages build-posts]]))

(def pages-root "templates/md/pages")
(def posts-root "templates/md/posts")

(defn load-content []
  {:pages (build-pages pages-root)
   :posts (build-posts posts-root)})

