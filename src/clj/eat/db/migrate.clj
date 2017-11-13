(ns eat.db.migrate
  (:require [clojure.java.jdbc :as sql]))

(def posts-table
  (sql/create-table-ddl
   :posts
   [[:id :serial]
    [:title :text "UNIQUE" "NOT NULL"]
    [:author :text "NOT NULL"]
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

(def migrations-table
  (sql/create-table-ddl
   :migrations
   [[:name :varchar "NOT NULL"]
    [:created_at :timestamp
     "NOT NULL"  "DEFAULT CURRENT_TIMESTAMP"]]))

;;Migrations
(defn initial-schema [db-spec]
  (sql/db-do-commands db-spec
                      [posts-table
                       users-table]))

(defn add-active-post-field [db-spec]
  (sql/db-do-commands db-spec
                      ["ALTER TABLE posts ADD COLUMN active BOOLEAN"
                       "UPDATE posts SET active = 't'"
                       "ALTER TABLE posts ALTER COLUMN active SET NOT NULL"
                       "ALTER TABLE posts ALTER COLUMN active SET DEFAULT TRUE"]))

(def migrations
  [#'initial-schema
   #'add-active-post-field])

(defn run-and-record-migration! [migration db-spec]
  (let [name (-> migration meta :name str)
        created_at (java.sql.Timestamp. (System/currentTimeMillis))]
    (println "Running migration:" name)
    (try (migration db-spec)
         (catch Exception _))
    (sql/insert! db-spec
                 :migrations
                 {:name name
                  :created_at created_at})))

(defn find-migrations-already-run [db-spec]
  (sql/query db-spec ["select name from migrations"]
             {:row-fn :name
              :result-set-fn set}))

(defn migrate! [db]
  (try (sql/db-do-commands db migrations-table)
       (catch Exception _))
  (let [run-migrations (find-migrations-already-run db)]
    (doseq [migration migrations
            :when (not (run-migrations (str (:name (meta migration)))))]
      (run-and-record-migration! migration db))))
