(ns eat.content
  (:require [eat.io :refer [load-edn build-edn!]]))

;; TODO: Build this into a config file

(def root "templates/md/posts")
(def target "resources/posts.edn")

(defn load-content! []
  (build-edn! root target)
  {:posts (load-edn "posts.edn")})


