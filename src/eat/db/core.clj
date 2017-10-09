(ns eat.db.core
  (:require [eat.db.util :refer [build-posts spit-file!]]
            [eat.layout.util :refer [string->set]]))

(def posts-root "posts/md")
(def posts-prefix "/post")
(def pages-prefix "/")
(def default-author "Shell")
(def number-of-most-recent-posts 5)

(def post-dir-path "posts/md")

(def ^:dynamic *posts*
  (let [posts-obj (build-posts posts-root)]
    (zipmap (map :url posts-obj) posts-obj)))

(defn get-post [url]
  (get *posts* url))

(defn create-or-update-post! [{:strs [title author date content preview preview-img tags] :as items}]
  (let [meta {:title title :author author :date date :preview preview :preview-img preview-img :tags ["tag1 tag2"] #_(string->set tags)
              } ;Note that content is not part of meta
        cont (str meta "\n" content)]
    (spit-file! "posts/md" "aha.md" cont)))
