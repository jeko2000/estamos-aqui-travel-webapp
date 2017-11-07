(ns eat.server
  (:require [ring.adapter.jetty :as jetty]))

(defn start-server [config handler]
  (let [port (or (get-in config [:server :port]) 3000)]
    (jetty/run-jetty handler {:join? false
                              :port 3000})))
