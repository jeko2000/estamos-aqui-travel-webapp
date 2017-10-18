(ns eat.core
  (:require [eat.config :refer [config]]
            [eat.server :refer [start-server]]
            [eat.handler :refer [handler]])
  (:gen-class))

(defn -main []
  (start-server config handler))
