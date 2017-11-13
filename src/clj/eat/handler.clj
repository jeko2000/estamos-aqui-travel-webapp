(ns eat.handler
  (:require [eat.layout :as layout]
            [eat.routes.admin :refer [admin-routes]]
            [eat.routes.user :refer [user-routes]]
            [eat.routes.services :refer [service-routes]]
            [compojure.core :refer [routes]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.backends.session :refer [session-backend]]))

(def app-routes
  (routes
   user-routes
   admin-routes
   (wrap-restful-format service-routes)
   #_(route/resources "/")
   (route/not-found (layout/error {:error-title "Page Not Found"
                                   :status 404}))))

(def custom-error-response
  (let [status 403]
    {:status  status
     :headers {"Content-Type" "text/html"}
     :body    (layout/error {:error-title "Invalid anti-forgery token"
                             :status status})}))

(def augmented-site-defaults
  (-> site-defaults
      (assoc-in [:security :anti-forgery]
                {:error-response custom-error-response})))

(def backend (session-backend))
(def handler (-> #'app-routes
                 (wrap-defaults augmented-site-defaults)
                 (wrap-authentication backend)
                 (wrap-authorization backend)))
