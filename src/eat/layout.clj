(ns eat.layout
  (:require [eat.layout.index :refer [index-page]]
            [eat.layout.post :refer [post-page]]
            [eat.layout.tag :refer [tag-page]]
            [eat.config :refer [config]]
            [eat.db.core :refer [*posts* get-post]]
            [eat.layout.admin.index :refer [admin-page]]
            [eat.layout.admin.edit :refer [edit-page]]))

(defn index []
  (index-page (:layout config) (vals *posts*)))

(defn post [url]
  (let [post-obj (get-post url)]
    (post-page (:layout config) post-obj (vals *posts*))))

(defn tag [target-tag]
  (tag-page (:layout config) target-tag (vals *posts*)))


(defn admin []
  (admin-page (:layout config) (map #(select-keys % [:title :date :tags :url]) (vals *posts*))))

(defn edit [url]
  (let [post-obj (get-post url)]
    (edit-page (:layout config) post-obj)))
