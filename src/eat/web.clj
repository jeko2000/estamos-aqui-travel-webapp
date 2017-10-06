(ns eat.web
  (:require [optimus.assets :as assets]
            [optimus.link :as link]
            [optimus.export :as export]
            [optimus.optimizations :as optimizations]      
            [optimus.prime :as optimus]                    
            [optimus.strategies :refer [serve-live-assets]]
            [stasis.core :as stasis]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.content-type :refer [wrap-content-type]]   
            [clojure.string :as str]
            [eat.layout :refer [get-pages]]
            [eat.posts :refer [build-posts]]))

(def posts-root "posts/md/posts")
(def posts (build-posts posts-root))

(defn get-assets []
  (assets/load-assets "public" [#"img/.*"
                                #"css/.*"
                                #"js/.*"]))

(def optimize optimizations/all)

(defn create-app []
  (-> (get-pages posts)
      stasis/serve-pages
      wrap-exceptions
      (optimus/wrap get-assets optimize serve-live-assets)
      wrap-content-type))

(def app (create-app))

(def export-directory "./build")

(defn- load-export-directory []
  (stasis/slurp-directory export-directory #"\.[^.]+$"))

(defn export []
  (let [assets (optimize (get-assets) {})]
    (print "Exporting blog...")
    (stasis/empty-directory! export-directory)
    (optimus.export/save-assets assets export-directory)
    (stasis/export-pages (get-pages) export-directory {:optimus-assets assets})
    (println "Done")
    (println)))
