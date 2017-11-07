(ns eat.core
  (:require [reagent.core :as reagent :refer [atom]]
            [eat.components :refer [image link-to instagram-gallery]]
            [ajax.core :refer [GET POST]]))

(defn get-instagram-images [images]
  (GET "/api/instagram"
       {:params {:count 6 :resolution "thumbnail"}
        :format :transit
        :response-format :transit
        :handler #(reset! images (:body %))
        :error-handler #(do
                          (.log js/console (str "error: " %))
                          (reset! images nil))}))
(defn instagram-component []
  (let [images (atom nil)]
    (get-instagram-images images)
    (fn []
      [:div
       [instagram-gallery @images]])))

(reagent/render
 [instagram-component]
 (.getElementById js/document "instagram-wrapper"))
