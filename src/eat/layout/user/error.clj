(ns eat.layout.user.error
  (:require [eat.layout.user.base :refer [base]]
            [eat.layout.components :refer [sticky-footer-fix]]
            [hiccup.element :refer [link-to]]))

(defn error-main [error status]
  [:section {:id "main"}
   [:br]
   [:h2 [:b "Page Not Found. " [:small "Status " (or status 404)]]]
   (if error [:p error])
   [:p "Please consider returning back to our " (link-to "/" "homepage") "."]])

(defn error-page [layout-config {:keys [error status posts tags]}]
  (base layout-config
        {:title "Page not found"
         :content (error-main error status)
         :posts posts
         :tags tags
         :js (sticky-footer-fix)}))
