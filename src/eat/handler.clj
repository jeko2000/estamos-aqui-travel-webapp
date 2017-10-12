(ns eat.handler
  (:require [compojure.core :refer [GET POST defroutes routes context]]
            [compojure.route :as route]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.flash :refer [wrap-flash]]
            [ring.middleware.session :refer [wrap-session]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.accessrules :refer [restrict wrap-access-rules]]
            [ring.util.response :as response]
            [eat.layout :as layout]
            [eat.routes.admin :refer [admin-routes]]
            [eat.auth :refer [post-login post-logout]]
            [eat.db.core :refer [get-user]]))

(defroutes base-routes
  (GET "/" [] (layout/index))
  (GET "/login" req (layout/login req))
  (POST "/login" req (post-login req))
  (POST "/logout" req (post-logout req)))

(defroutes post-routes
  (GET "/post/:id" [id] (layout/post (str "/post/" id))))

(defroutes tag-routes
  (GET "/tags/:id" [id] (layout/tag id)))

(defn autheticated? [{:keys [session]}]
  (boolean (:identity session)))

(def app-routes
  (routes
   base-routes
   post-routes
   tag-routes
   (context "/admin" []
            (restrict admin-routes {:handler autheticated?}))
   (route/resources "/")
   (route/not-found (layout/error {:error "Not Found"
                                   :status 404}))))

(def backend (session-backend))
(def handler (-> #'app-routes
                 (wrap-authentication backend)
                 (wrap-authorization backend)
                 wrap-flash
                 wrap-session
                 wrap-reload
                 wrap-params
                 wrap-multipart-params))
