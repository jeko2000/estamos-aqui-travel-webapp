(ns eat.layout
  (:require [eat.layout.user.index :refer [index-page]]
            [eat.layout.user.post :refer [post-page]]
            [eat.layout.user.tag :refer [tag-page]]
            [eat.layout.user.disclaimer :refer [disclaimer-page]]
            [eat.layout.user.about-us :refer [about-us-page]]
            [eat.layout.user.login :refer [login-page]]
            [eat.layout.user.error :refer [error-page]]
            [eat.layout.user.search :refer [search-page search-page-no-results]]
            [eat.layout.user.author :refer [author-page]]
            [eat.layout.user.all-posts :refer [all-posts-page]]
            [eat.layout.admin.index :refer [admin-page]]
            [eat.layout.admin.post :refer [admin-post-page]]
            [eat.rss :refer [atom-feed]]
            [eat.config :refer [config]]))

(defn index [posts]
  (index-page (:layout @config) posts))

(defn post [post-obj posts]
  (post-page (:layout @config) post-obj posts))

(defn tag [target-tag posts-with-tag]
  (tag-page (:layout @config) target-tag posts-with-tag))

(defn all-posts [posts]
  (all-posts-page (:layout @config) posts))

(defn author [target-author posts-by-author]
  (author-page (:layout @config) target-author posts-by-author))

(defn disclaimer []
  (disclaimer-page (:layout @config)))

(defn about-us [posts]
  (about-us-page (:layout @config) posts))

(defn user-search [safe-query result-posts]
  (search-page (:layout @config) safe-query result-posts))

(defn user-search-no-results [safe-query]
      (search-page-no-results (:layout @config) safe-query))

(defn admin [all-posts]
  (admin-page (:layout @config) all-posts))

(defn edit-post [post-obj]
  (admin-post-page (:layout @config) "/admin/edit-post" post-obj))

(defn new-post [post-obj]
  (admin-post-page (:layout @config) "/admin/new-post" post-obj))

(defn login [{:keys [flash] :as req}]
  (login-page (:layout @config) flash))

(defn error [options]
  (error-page (:layout @config) options))

(defn rss [posts]
  (atom-feed posts))
