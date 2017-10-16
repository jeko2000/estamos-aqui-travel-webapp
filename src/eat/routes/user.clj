(ns eat.routes.user
  (:require [eat.auth :as auth]
            [eat.layout :as layout]
            [compojure.core :refer [GET POST defroutes routes]]
            [ring.util.response :as response]))

(defroutes home-routes
  (GET "/" [] (layout/index)))

(defroutes post-routes
  (GET "/posts/:id" [id] (layout/post (str "/posts/" id))))

(defroutes tag-routes
  (GET "/tags/:id" [id] (layout/tag id)))

(defroutes misc-routes
  (GET "/disclaimer" _ (layout/disclaimer)))

(defroutes logging-routes
  (GET "/login" req (if (auth/autheticated? req)
                      (response/redirect "/admin")
                      (layout/login req)))
  (POST "/login" req (auth/handle-login req))
  (POST "/logout" req (auth/handle-logout req)))

(def user-routes
  (routes
   home-routes
   post-routes
   tag-routes
   logging-routes
   misc-routes))
