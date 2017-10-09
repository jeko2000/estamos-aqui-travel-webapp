(ns eat.handler
  (:require [compojure.core :refer [GET defroutes routes]]
            [compojure.route :as route]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :as response]
            [eat.layout :as layout]
            [eat.routes.admin :refer [admin-routes]]))

(defroutes base-routes
  (GET "/" [] (layout/index)))

(defroutes post-routes
  (GET "/post/:id" [id] (layout/post (str "/post/" id))))

(defroutes tag-routes
  (GET "/tags/:id" [id] (layout/tag id)))

(def app-routes
  (routes
   base-routes
   post-routes
   tag-routes
   admin-routes
   (route/resources "/")
   (route/not-found "Page not found")))

(def handler (-> #'app-routes
                 wrap-reload
                 wrap-params))
