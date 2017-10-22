(ns eat.handler
  (:require [eat.layout :as layout]
            [eat.routes.admin :refer [admin-routes]]
            [eat.routes.user :refer [user-routes]]
            [compojure.core :refer [routes]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.backends.session :refer [session-backend]]))

(def app-routes
  (routes
   user-routes
   admin-routes
   #_(route/resources "/")
   (route/not-found (layout/error {:error-title "Page Not Found"
                                   :status 404}))))

(def backend (session-backend))
(def handler (-> #'app-routes
                 (wrap-defaults site-defaults)                 
                 (wrap-authentication backend)
                 (wrap-authorization backend)))
