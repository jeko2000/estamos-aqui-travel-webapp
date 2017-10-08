(ns eat.db.core
  (:require [eat.db.util :refer [build-posts]]))

(def posts-root "posts/md")
(def posts-prefix "/post")
(def pages-prefix "/")
(def default-author "Shell")
(def number-of-most-recent-posts 5)

(def posts
  (let [posts-obj (build-posts posts-root)]
    (zipmap (map :url posts-obj) posts-obj)))

(defn get-post [url]
  (get posts url))
