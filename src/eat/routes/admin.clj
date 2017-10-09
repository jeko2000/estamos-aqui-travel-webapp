(ns eat.routes.admin
  (:require [compojure.core :refer [GET POST defroutes context]]
            [ring.util.response :as response]
            [eat.layout :as layout]
            [eat.db.core :refer [create-or-update-post!]]))

(defn edit-post! [{:keys [params]}]
  (create-or-update-post! params)
  (response/redirect "/admin"))

(defroutes admin-routes
  (context "/admin" []
           (GET "/" [] (layout/admin))
           (GET "/edit/post/:id" [id] (layout/edit (str "/post/" id)))
           (POST "/edit" request (edit-post! request))))
