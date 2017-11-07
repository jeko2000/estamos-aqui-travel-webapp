(ns eat.routes.user
  (:require [eat.auth :as auth]
            [eat.layout :as layout]
            [eat.search :refer [search]]            
            [compojure.core :refer [GET POST defroutes routes]]
            [hiccup.util :refer [escape-html]]            
            [ring.util.response :as response]))

(defn handle-search [{:keys [params]}]
  (let [safe-query (-> params :q escape-html)]
    (if (clojure.string/blank? safe-query)
      (response/redirect "/")
      (let [result-posts (search safe-query)]
        (if (empty? result-posts)
          (layout/user-search-no-results safe-query)
          (layout/user-search safe-query result-posts))))))

(defroutes home-routes
  (GET "/" [] (layout/index))
  (GET "/about-us" [] (layout/about-us))
  (GET "/search" req (handle-search req)))

(defroutes post-routes
  (GET "/posts/:id" [id] (layout/post (str "/posts/" id))))

(defroutes tag-routes
  (GET "/tags/:id" [id] (layout/tag id)))

(defroutes misc-routes
  (GET "/disclaimer" _ (layout/disclaimer))
  (GET "/feed.atom" _ (layout/rss)))

(defroutes logging-routes
  (GET "/login" req (if (auth/authenticated? req)
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
