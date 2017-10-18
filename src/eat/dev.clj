(ns eat.dev
  (:require [eat.config :refer [config]]
            [eat.server :refer [start-server]]
            [eat.handler :refer [handler]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.file :refer [wrap-file]])
  (:gen-class))

(defn -main []
  (start-server config (-> #'handler
                           wrap-reload
                           (wrap-file "/var/www"))))

