(ns eat.routes.admin
  (:require [eat.layout :as layout]
            [eat.db :refer [update-post! insert-post! delete-post! find-post-by-url find-post]]
            [eat.db.core :refer [*db*]]
            [eat.util :refer [copy-file!]]
            [eat.validation :refer [validate-post]]
            [eat.auth :refer [authenticated?]]
            [compojure.core :refer [GET POST defroutes context]]
            [ring.util.http-response :refer [found]]
            [buddy.auth.accessrules :refer [restrict]]))

(defn unauthorized-handler [error-title]
  (let [status 401]
    (fn [req val]
      {:status status
       :headers {"Content-Type" "text/html"}
       :body (layout/error {:status 401
                            :error-title error-title})})))

(defn keywordize-map [params]
  (zipmap (map keyword (keys params))
          (vals params)))

(defn- save-upload! [{:keys [tempfile filename] :as file}]
  (copy-file! tempfile "/var/www/static/img/" filename))

(defn- handle-image-upload [{:keys [size filename] :as file-obj}]
  (if (and (> size 0)
           (not (empty? filename)))
    (save-upload! file-obj)))

(defn- handle-image-uploads [{:keys [file]}]
  (let [uploads (if (vector? file) file (vector file))]
    (doseq [upload uploads]
      (handle-image-upload upload))))

(defn handle-new-post! [{:keys [params]}]
  (if-let [errors (validate-post params)]
    (-> (found "/admin/new-post")
        (assoc :flash (assoc params :errors errors)))
    (do
      (insert-post! *db* (keywordize-map params))
      (handle-image-uploads params)
      (found "/admin"))))

(defn handle-edit-post! [{:keys [params]}]
  (let [post-obj (find-post *db* (:id params))]
    (if-let [errors (validate-post params)]
      (-> (found (str "/admin/edit" (:url post-obj)))
          (assoc :flash (assoc params :errors errors)))
      (do
        (update-post! *db* (keywordize-map params))
        (handle-image-uploads params)
        (found "/admin")))))

(defn handle-delete-post! [{:keys [headers] :as req}]
  (let [post (as-> headers $
               (get $"referer")
               (clojure.string/replace $ #".*/edit" "")
               (find-post-by-url *db* $))]
    (delete-post! *db* (:id post))
    (found "/admin")))

(defroutes restricted-routes
  (GET "/" [] (layout/admin))
  (GET "/edit/posts/:url" req (layout/edit-post req))
  (GET "/new-post" [] layout/new-post)
  (POST "/edit-post" request (handle-edit-post! request))
  (POST "/new-post" request (handle-new-post! request))
  (POST "/delete-post" request (handle-delete-post! request)))

(def admin-routes
  (context "/admin" []
           (restrict restricted-routes {:handler authenticated?
                                        :reject-handler (unauthorized-handler "Unauthorized")})))
