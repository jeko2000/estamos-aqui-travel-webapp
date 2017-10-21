(ns eat.layout
  (:require [eat.layout.user.index :refer [index-page]]
            [eat.layout.user.post :refer [post-page]]
            [eat.layout.user.tag :refer [tag-page]]
            [eat.layout.user.disclaimer :refer [disclaimer-page]]
            [eat.layout.user.about-us :refer [about-us-page]]
            [eat.layout.user.login :refer [login-page]]
            [eat.layout.user.error :refer [error-page]]
            [eat.layout.user.search :refer [search-page search-page-no-results]]
            [eat.layout.admin.index :refer [admin-page]]
            [eat.layout.admin.post :refer [admin-post-page]]
            [eat.config :refer [config]]
            [eat.db :refer [find-post find-posts find-posts-with-tag find-post-by-url find-tags]]
            [eat.db.core :refer [*db*]]))

(defn index []
  (index-page (:layout @config) (find-posts *db*)))

(defn post [url]
  (let [post-obj (find-post-by-url *db* url)]
    (post-page (:layout @config) post-obj (find-posts *db*))))

(defn tag [target-tag]
  (tag-page (:layout @config) target-tag (find-posts-with-tag *db* target-tag)))

(defn disclaimer []
  (disclaimer-page (:layout @config)))

(defn about-us []
  (about-us-page (:layout @config) (find-posts *db*)))

(defn user-search [safe-query result-posts]
  (search-page (:layout @config) safe-query result-posts))

(defn user-search-no-results [safe-query]
      (search-page-no-results (:layout @config) safe-query))

(defn admin []
  (admin-page (:layout @config) (map #(select-keys % [:title :date :tags :url]) (find-posts *db*))))

(defn edit-post [url]
  (let [post-obj (find-post-by-url *db* url)]
    (admin-post-page (:layout @config) "/admin/edit-post" post-obj)))

(defn new-post [req]
  (admin-post-page (:layout @config) "/admin/new-post"{}))

(defn login [{:keys [flash] :as req}]
  (login-page (:layout @config) flash))

(defn error [options]
  (error-page (:layout @config) (assoc options :posts (find-posts *db*) :tags (find-tags (find-posts *db*)))))
