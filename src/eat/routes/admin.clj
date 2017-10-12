(ns eat.routes.admin
  (:require [compojure.core :refer [GET POST defroutes context]]
            [ring.util.response :as response]
            [eat.layout :as layout]
            [eat.db.core :refer [update-post! update-uploads!]]
            [buddy.auth.accessrules :refer [restrict]]))

(defn handle-form-submission! [{:keys [params]}]
  (update-post! params)
  (update-uploads! params)
  (response/redirect "/admin"))

(defroutes admin-routes
  (GET "/" [] (layout/admin))
  (GET "/2" [] (layout/admin))           
  (GET "/edit/post/:id" [id] (layout/edit (str "/post/" id)))
  (GET "/new-post" [] layout/new-post)
  (POST "/edit" request (handle-form-submission! request)))

