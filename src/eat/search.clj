(ns eat.search
  (:require [eat.config :refer [config]]
            [eat.db.core :refer [*db*]]
            [eat.db :refer [find-posts]]
            [clojure.java.io :as io]
            [clucy.core :as clucy]))

(def searchable-fields [:preview :md :preview_img :title :url])
(def index-meta {:md {:stored false}})
(def ^:dynamic *index* (clucy/disk-index (:search-index-dir @config)))

(defn- reset-index! []
  (doseq [index-file (-> (:search-index-dir @config)
                         io/file
                         file-seq
                         rest)]
    (io/delete-file index-file true)))

(defn index-post! [post]
  (let [searchable (select-keys post searchable-fields)]
    (clucy/add *index*
               (with-meta searchable
                 index-meta))))

(defn index-posts! [posts]
  (doseq [post posts]
    (index-post! post)))

(defn flush-and-index-all-posts! []
  (reset-index!)
  (index-posts! (find-posts *db*)))

(defn unindex-post! [{:keys [title]}]
  (clucy/search-and-delete *index*
                           (str "title:" title)))

(defn search [query]
  (clucy/search *index* query (or (:max-search-results @config) 10)))
