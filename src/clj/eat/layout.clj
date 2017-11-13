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
            [eat.layout.admin.index :refer [admin-page]]
            [eat.layout.admin.post :refer [admin-post-page]]
            [eat.rss :refer [atom-feed]]
            [eat.config :refer [config]]
            [eat.db :as db]
            [eat.db.core :refer [*db*]]))

(defn index []
  (index-page (:layout @config) (db/find-posts *db*)))

(defn post [url]
  (let [post-obj (db/find-post-by-url-with-pager-links *db* url)]
    (post-page (:layout @config) post-obj (db/find-posts *db*))))

(defn tag [target-tag]
  (tag-page (:layout @config) target-tag (db/find-posts-with-tag *db* target-tag)))

(defn author [author]
  (author-page (:layout @config) author (db/find-posts-by-author *db* author)))

(defn disclaimer []
  (disclaimer-page (:layout @config)))

(defn about-us []
  (about-us-page (:layout @config) (db/find-posts *db*)))

(defn user-search [safe-query result-posts]
  (search-page (:layout @config) safe-query result-posts))

(defn user-search-no-results [safe-query]
      (search-page-no-results (:layout @config) safe-query))

(defn admin []
  (admin-page (:layout @config) (map #(select-keys % [:title :date :tags :url]) (db/find-posts *db*))))

(defn edit-post [{:keys [params flash]}]
  (admin-post-page (:layout @config) "/admin/edit-post" (if (:errors flash)
                                                          flash
                                                          (db/find-post-by-url-with-pager-links *db* (str "/posts/" (:url params))))))

(defn new-post [{:keys [flash]}]
  (admin-post-page (:layout @config) "/admin/new-post" (if (:errors flash) flash {})))

(defn login [{:keys [flash] :as req}]
  (login-page (:layout @config) flash))

(defn error [options]
  (error-page (:layout @config) (assoc options :posts (db/find-posts *db*) :tags (db/find-tags (db/find-posts *db*)))))

(defn rss []
  (atom-feed (db/find-posts *db*)))
