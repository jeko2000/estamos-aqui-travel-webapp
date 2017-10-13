(ns eat.core
  (:require [eat.config :refer [config]]
            [eat.server :refer [start-server]]
            [eat.db.core :refer [create-user!]]            
            [eat.handler :refer [handler]]))

(defn -main []
  (create-user! {:username "admin" :password "1234"})
  (start-server config handler))

