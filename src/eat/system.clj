(ns eat.system
  (:require [eat.web :as web]
            [eat.log :refer [info]]
            [eat.server :as server]
            [prone.middleware :refer [wrap-exceptions]]))

(defn start [system]
  (let [port (:port system)
        handler (wrap-exceptions (web/create-app))
        server (server/start-jetty handler :port port)]
    (info "Starting app on port " port ".")
    (assoc system
           :handler handler
           :server server)))

(defn stop [system]
  (if-let [server (:server system)]
    (server/stop-jetty server)
    (info "Stopping app."))
  (dissoc system :server :handler))

(defn create-system []
  {:port 8080
   :start start
   :stop stop})

(defn -main [& args]
  (let [system (create-system)]
    (start system)))
