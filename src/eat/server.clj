(ns eat.server
  (:require [ring.adapter.jetty :as jetty]))

(defn start-jetty
  [handler & {:keys [port]}]
  (let [port (or port 8080)]
    (jetty/run-jetty handler {:port port :join? false})))

(defn stop-jetty
  [server]
  (.stop server))
