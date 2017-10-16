(ns eat.core
  (:require [eat.config :refer [config]]
            [eat.server :refer [start-server]]
            [eat.handler :refer [handler]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn -main []
  (start-server config handler))

(defn -main-dev []
  (start-server config (wrap-reload handler)))
