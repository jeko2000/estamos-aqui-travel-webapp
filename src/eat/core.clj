(ns eat.core
  (:require [eat.config :refer [config]]
            [eat.server :refer [start-server]]
            [eat.handler :refer [handler]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn -main []
  (log/info  "Running main application")
  (start-server config handler))

(defn -main-dev []
  (println (java.util.Date.) "Running dev application")
  (start-server (wrap-reload config) handler))
