(ns eat.layout
  (:require [eat.layout.user.index :refer [index-page]]
            [eat.layout.user.post :refer [post-page]]
            [eat.layout.user.tag :refer [tag-page]]
            [eat.layout.user.login :refer [login-page]]
            [eat.layout.user.error :refer [error-page]]
            [eat.config :refer [config]]
            [eat.db.core :refer [*posts* get-post get-all-tags]]
            [eat.layout.admin.index :refer [admin-page]]
            [eat.layout.admin.post :refer [admin-post-page]]))

(defn index []
  (index-page (:layout @config) (vals *posts*)))

(defn post [url]
  (let [post-obj (get-post url)]
    (post-page (:layout @config) post-obj (vals *posts*))))

(defn tag [target-tag]
  (tag-page (:layout @config) target-tag (vals *posts*)))

(defn admin []
  (admin-page (:layout @config) (map #(select-keys % [:title :date :tags :url]) (vals *posts*))))

(defn edit [url]
  (let [post-obj (get-post url)]
    (admin-post-page (:layout @config) post-obj)))

(defn new-post [req]
  (admin-post-page (:layout @config) {}))

(defn login [{:keys [flash] :as req}]
  (login-page (:layout @config) flash))

(defn error [options]
  (error-page (:layout @config) (assoc options :posts (vals *posts*) :tags (get-all-tags *posts*))))
