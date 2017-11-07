(ns eat.routes.services
  (:require [eat.api :refer [instagram-get-recent-images]]
            [eat.validation :refer [validate-instagram]]
            [ring.util.response :as response]
            [compojure.core :refer [GET POST defroutes context]]))

(defn handle-instagram [{:keys [params]}]
  (if-let [errors (validate-instagram params)]
    {:status 400
     :headers {}
     :body {:status :not-ok
            :errors errors}}
    (try      
      (response/response {:status :ok
                          :body (instagram-get-recent-images (:count params) (:resolution params))})
      (catch Exception e
        {:status 500
         :headers {}
         :body {:status :not-ok
                :errors {:server-error ["Unable to fetch images!"]}}}))))

(defroutes service-routes
  (context "/api" []
           (GET "/instagram" [] handle-instagram)))
