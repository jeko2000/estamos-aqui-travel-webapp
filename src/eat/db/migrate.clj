(ns eat.db.migrate
  (:require [clojure.java.jdbc :as sql]))

(def ^:dynamic *db* "jdbc:postgresql://localhost:5432/eat-db")

(defn migrated? [db-spec]
  (-> (sql/query db-spec ["select count(*) from information_schema.columns where table_name='posts'"])
      first
      :count
      pos?))

(def posts-table
  (sql/create-table-ddl
   :posts
   [[:id :serial]
    [:title :text "UNIQUE" "NOT NULL"]
    [:author :text "UNIQUE" "NOT NULL"]
    [:date :text "NOT NULL"]
    [:md :text "NOT NULL"]    
    [:content :text "NOT NULL"]
    [:preview :text]
    [:preview_img :text]
    [:title_img :text]
    [:tags :text]
    [:url :text]]))

(def users-table
  (sql/create-table-ddl
   :users
   [[:id :serial]
    [:username :text "UNIQUE" "NOT NULL"]
    [:password :text "NOT NULL"]
    [:created :timestamp "DEFAULT CURRENT_TIMESTAMP"]]))

(defn initial-schema [db-spec]
  (sql/db-do-commands db-spec
                      [posts-table
                       users-table]))

(def migrations
  [initial-schema])

(defn migrate! [db-spec]
  (sql/with-db-transaction [t-conn db-spec]
    (doseq [migration migrations]
      (migration t-conn))))

;;(sql/db-do-commands *db* "DROP table users")
;;(migrate! *db*)
