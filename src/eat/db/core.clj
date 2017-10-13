(ns eat.db.core
  (:require [eat.db.util :refer [build-posts spit-file! copy-file!]]
            [eat.util :refer [normalize-title]]
            [eat.layout.components :refer [string->set]]
            [buddy.hashers :as hashers]
            [clojure.set :refer [union]]))

(def posts-root "posts/md")
(def posts-prefix "/post")
(def pages-prefix "/")
(def number-of-most-recent-posts 5)

(def post-dir-path "posts/md")

(def ^:dynamic *posts*
  (let [posts-obj (build-posts posts-root)]
    (zipmap (map :url posts-obj) posts-obj)))

(defn get-post [url]
  (get *posts* url))

(defn update-post! [{:strs [title author date preview preview-img title-img tags content] :as params}]
  (let [meta {:title title :author author :date date :preview preview :preview-img preview-img :title-img title-img :tags (string->set tags)} ;Note that content is not part of meta
        cont (str meta "\n\n\n" content)]
    (spit-file! "posts/md" (normalize-title title) cont)))

(defn- update-upload! [{:keys [tempfile filename] :as file}]
  (copy-file! tempfile "public/img" filename))

(defn update-uploads! [{:strs [file]}]
  (if (and (> (:size file) 0)
           (not (empty? (:filename file))))
    (let [uploads (if (vector? file) file (vector file))]
      (doseq [upload uploads]
        (update-upload! upload)))))

;;tags
(defn contains-tag? [tag post]
  (if-let [tags (:tags post)]
    (contains? tags tag)))

(defn get-all-tags [posts]
  (->> posts
       (map :tags)
       (apply union)
       (remove nil?)))

;;users
(def userstore (atom {})) ;;To mimick database

(defn str->keyword [string]
  (keyword (clojure.string/replace string #"\s+" "-")))

(defn create-user! [{:keys [username password] :as user}]
  (let [user-id (java.util.UUID/randomUUID)]
    (-> user
        (assoc :password (hashers/encrypt password)
               :uuid user-id)
        (->> (swap! userstore assoc user-id)))))

(defn get-user [id]
  (get @userstore id))

(defn get-user-from-creds [username password]
  (let [reducer (fn [_ user] (if (and (= username (:username user))
                                     (hashers/check password (:password user)))
                              (reduced user)))]
    (reduce reducer nil (vals @userstore))))
