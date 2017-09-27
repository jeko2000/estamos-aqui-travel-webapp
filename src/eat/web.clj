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
            [eat.pages :refer [create-pages]]
            [eat.content :refer [load-content!]]   
            [eat.layout :refer [render-page]]))

(defn get-assets []
  (assets/load-assets "public" [#".*"]))

(defn prepare-page [page content request]
  (-> page
      (render-page content request)))

(defn update-map
  ([m fv]
   (zipmap (keys m)
           (map fv (vals m))))
  ([m fk fv]
   (zipmap (map fk (keys m))
           (map fv (vals m)))))

(defn get-pages []
  (let [content (load-content!)]
    (-> content
        create-pages
        (update-map #(partial prepare-page % content)))))

(def optimize optimizations/all)

(defn create-app []
  (-> get-pages
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

(get-pages)
