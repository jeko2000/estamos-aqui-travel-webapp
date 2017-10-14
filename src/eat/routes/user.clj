(ns eat.routes.user
  (:require [eat.auth :refer [handle-login handle-logout]]
            [eat.layout :as layout]
            [compojure.core :refer [GET POST defroutes routes]]))

(defroutes home-routes
  (GET "/" [] (layout/index)))

(defroutes post-routes
  (GET "/posts/:id" [id] (layout/post (str "/posts/" id))))

(defroutes tag-routes
  (GET "/tags/:id" [id] (layout/tag id)))

(defroutes logging-routes
  (GET "/login" req (layout/login req))
  (POST "/login" req (handle-login req))
  (POST "/logout" req (handle-logout req)))

(def user-routes
  (routes
   home-routes
   post-routes
   tag-routes
   logging-routes))
