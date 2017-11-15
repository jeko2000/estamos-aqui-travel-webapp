(ns eat.routes.user
  (:require [eat.auth :as auth]
            [eat.layout :as layout]
            [eat.search :refer [search]]
            [eat.exports :refer [post-pdf]]
            [eat.db.core :refer [*db*]]
            [eat.db :refer [find-post-by-url]]
            [compojure.core :refer [GET POST defroutes routes]]
            [hiccup.util :refer [escape-html]]
            [ring.util.http-response :refer [ok found]]))

(defn handle-search [{:keys [params]}]
  (let [safe-query (-> params :q escape-html)]
    (if (clojure.string/blank? safe-query)
      (found "/")
      (let [result-posts (search safe-query)]
        (if (empty? result-posts)
          (layout/user-search-no-results safe-query)
          (layout/user-search safe-query result-posts))))))

(defn write-response [pdf-bytes]
  (with-open [in (java.io.ByteArrayInputStream. pdf-bytes)]
    (-> (ok in)
        (assoc-in [:headers "Content-Disposition"] "filename=document.pdf")
        (assoc-in [:headers "Content-Length"] (count pdf-bytes))
        (assoc-in [:headers "Content-Type"] "application/pdf"))))

(defn handle-post-pdf [url]
  (if-let [post (find-post-by-url *db* url)]
    (try
      (let [out (java.io.ByteArrayOutputStream.)]
        (post-pdf post out)
        (write-response (.toByteArray out)))
      (catch Exception e
        (layout/error {:error-title "Unable to create PDF report"
                       :status 404})))))

(defroutes home-routes
  (GET "/" [] (layout/index))
  (GET "/search" req (handle-search req))
  (GET "/about-us" [] (layout/about-us))
  (GET "/disclaimer" _ (layout/disclaimer)))

(defroutes post-routes
  (GET "/posts/:id" [id] (layout/post (str "/posts/" id))))

(defroutes tag-routes
  (GET "/tags/:id" [id] (layout/tag id)))

(defroutes author-routes
  (GET "/authors/:id" [id] (layout/author id)))

(defroutes misc-routes
  (GET "/feed.atom" _ (layout/rss)))

(defroutes export-routes
  (GET "/posts/:url/export" [url] (handle-post-pdf (str "/posts/" url))))

(defroutes logging-routes
  (GET "/login" req (if (auth/authenticated? req)
                      (found "/admin")
                      (layout/login req)))
  (POST "/login" req (auth/handle-login req))
  (POST "/logout" req (auth/handle-logout req)))

(def user-routes
  (routes
   home-routes
   post-routes
   tag-routes
   author-routes
   logging-routes
   export-routes
   misc-routes))
