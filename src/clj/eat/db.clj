(ns eat.db
  (:require [eat.layout.components :refer [string->set]]
            [eat.util :refer [title->url string->boolean]]
            [clojure.java.jdbc :as sql]
            [buddy.hashers :as hashers]
            [me.raynes.cegdown :as md]))

;;Posts
(defn deserialize-post [post]
  (-> post
      (update :tags string->set)))

(defn serialize-post [{:keys [title md active] :as post}]
  (-> post
      (assoc :content (md/to-html md))
      (assoc :url (title->url title))
      (update :active string->boolean)
      (dissoc :id)))

(defn find-posts [db]
  (let [posts (sql/query db ["select * from posts where active = 't' order by date desc"])]
    (if-not (empty? posts)
      (map deserialize-post posts)
      posts)))

(defn find-active-and-innactive-posts [db]
  (let [posts (sql/query db ["select * from posts order by date desc"])]
    (if-not (empty? posts)
      (map deserialize-post posts)
      posts)))

(defn find-posts-with-tag [db target-tag]
  (let [posts (sql/query db [(str "select * from posts where tags like '%" target-tag "%' order by id desc")])]
    (if-not (empty? posts)
      (map deserialize-post posts)
      posts)))

(defn find-posts-by-author [db author]
  (let [posts (sql/query db [(str "select * from posts where author ilike '%" author "%' order by date desc")])]
    (if-not (empty? posts)
      (map deserialize-post posts)
      posts)))

(defn find-post [db id]
  (let [post (sql/query db [(str "select * from posts where id = " id " limit 1")]
                        {:result-set-fn first})]
    (if-not (empty? post)
      (deserialize-post post)
      post)))

(defn find-post-by-url [db url]
  (let [post (sql/query db [(str "select * from posts where url = '" url "' limit 1")]
                        {:result-set-fn first})]
    (if-not (empty? post)
      (deserialize-post post)
      post)))

(defn find-next-post [db id]
  (let [next-post (sql/query db [(str "select * from posts where id > " id " limit 1")]
                             {:result-set-fn first})]
    (if-not (empty? next-post)
      (deserialize-post next-post)
      next-post)))

(defn find-previous-post [db id]
  (let [previous-post (sql/query db [(str "select * from posts where id < " id " order by id desc limit 1")]
                                 {:result-set-fn first})]
    (if-not (empty? previous-post)
      (deserialize-post previous-post)
      previous-post)))

(defn find-post-by-url-with-pager-links [db url]
  (let [post (sql/query db [(str "select * from posts where url = '" url "' limit 1")]
                        {:result-set-fn first})]
    (if-not (empty? post)
      (let [curr-id (:id post)
            next (find-next-post db curr-id)
            previous (find-previous-post db curr-id)]
        (cond-> post
          true deserialize-post ;Always deserialize post
          next (assoc :next-post-url (:url next) :next-post-title (:title next))
          previous (assoc :previous-post-url (:url previous) :previous-post-title (:title previous))))
      post)))

(defn insert-post! [db post]
  (let [post-obj (select-keys post [:title :author :date :md :preview :preview_img :title_img :tags :active])]
    (sql/insert! db :posts (serialize-post post-obj))))

(defn update-post! [db post]
  (let [post-obj (select-keys post [:id :title :author :date :md :preview :preview_img :title_img :tags :active])]
    (sql/update! db :posts (serialize-post post-obj) [(str "id = " (:id post-obj))])))

(defn delete-post! [db id]
  (sql/delete! db :posts ["id = ?" id]))

;;Users

(defn deserialize-user [user]
  user)

(defn serialize-user [user]
  (-> user
      (update :password hashers/encrypt)))


(defn find-users [db]
  (let [users (sql/query db ["select * from users order by id desc"])]
    (if-not (empty? users)
      (map deserialize-user users)
      users)))

(defn find-user [db id]
  (let [user (sql/query db [(str "select * from users where id = " id " limit 1")]
                        {:result-set-fn first})]
    (if-not (empty? user)
      (deserialize-user user)
      user)))

(defn- find-user-by-username [db username]
  (let [user (sql/query db [(str "select * from users where username = '" username "' limit 1")]
                        {:result-set-fn first})]
    (if-not (empty? user)
      (deserialize-user user)
      user)))

(defn find-user-by-creds [db username password]
  (let [user (find-user-by-username db username)]
    (if (and (not-empty user)
             (hashers/check password (:password user)))
      user)))

(defn insert-user! [db user]
  (let [user-obj (select-keys user [:username :password])]
    (sql/insert! db :users (serialize-user user-obj))))

(defn update-user! [db id user]
  ;;Add let clause
  (sql/update! db :users (serialize-user user) ["id = ?" id]))

(defn delete-user! [db id]
  (sql/delete! db :users ["id = ?" id]))

;;Tags
(defn find-tags [post-or-posts]
  (let [posts (if (sequential? post-or-posts) post-or-posts (list post-or-posts))]
    (->> posts
         (map :tags)
         (remove nil?)
         (apply clojure.set/union))))
