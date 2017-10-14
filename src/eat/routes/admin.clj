(ns eat.routes.admin
  (:require [eat.layout :as layout]
            [eat.db :refer [update-post! insert-post!]]
            [eat.db.core :refer [*db*]]
            [eat.auth :refer [autheticated?]]
            [eat.db.util :refer [copy-file!]]
            [compojure.core :refer [GET POST defroutes context]]
            [ring.util.response :as response]
            [buddy.auth.accessrules :refer [restrict]]))

(defn keywordize-map [params]
  (zipmap (map keyword (keys params))
          (vals params)))

(defn handle-edit-post! [{:keys [params]}]
  (update-post! *db* (keywordize-map params))
  #_(copy-file! (:tempfile params) "public/img" (:filename params))
  (response/redirect "/admin"))

(defn handle-new-post! [{:keys [params]}]
  (insert-post! *db* (keywordize-map params))
  #_(copy-file! (:tempfile params) "public/img" (:filename params))
  (response/redirect "/admin"))

(defroutes restricted-routes
  (GET "/" [] (layout/admin))
  (GET "/2" [] (layout/admin))           
  (GET "/edit/posts/:url" [url] (layout/edit-post (str "/posts/" url)))
  (GET "/new-post" [] layout/new-post)
  (POST "/edit-post" request (handle-edit-post! request))
  (POST "/new-post" request (handle-new-post! request)))

(def admin-routes
  (context "/admin" []
           (restrict restricted-routes {:handler autheticated?})))

