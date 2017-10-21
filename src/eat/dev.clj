(ns eat.dev
  (:require [eat.config :refer [config]]
            [eat.init :refer [init]]
            [eat.server :refer [start-server]]
            [eat.handler :refer [handler]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.file :refer [wrap-file]])
  (:gen-class))

(defn -main []
  (init)
  (start-server config (-> #'handler
                           wrap-reload
                           (wrap-file "/var/www"))))
