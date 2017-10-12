(ns eat.core
  (:require [eat.config :refer [config]]
            [eat.server :refer [start-server]]
            [eat.handler :refer [handler]]
            [eat.db.core :refer [create-user!]]))

(defn -main []
  (create-user! {:username "admin" :password "1234"})
  (start-server config handler))


;; Authentication
;; Validation for new/edit blogs
