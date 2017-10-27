(ns eat.core
  (:require [eat.config :refer [config]]
            [eat.init :refer [init]]
            [eat.server :refer [start-server]]
            [eat.handler :refer [handler]])
  (:gen-class))

(defn -main []
  (init)
  (start-server config handler))
