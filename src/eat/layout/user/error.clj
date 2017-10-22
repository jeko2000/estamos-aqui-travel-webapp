(ns eat.layout.user.error
  (:require [eat.layout.user.base :refer [base]]
            [eat.layout.components :refer [sticky-footer-fix]]
            [hiccup.element :refer [link-to]]))

(defn error-main [error-title error status]
  [:section {:id "main"}
   [:br]
   [:h2 [:b (or error-title "Page not found")]]
   [:h4 " Status " (or status 404)]
   (if error [:p error])
   [:p "Please consider returning back to our " (link-to "/" "homepage") "."]])

(defn error-page [layout-config {:keys [error-title error status posts tags]}]
  (base layout-config
        {:title error-title
         :content (error-main error-title error status)
         :posts posts
         :tags tags
         :js (sticky-footer-fix)}))
