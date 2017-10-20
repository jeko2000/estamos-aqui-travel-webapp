(ns eat.layout.user.disclaimer
  (:require [eat.layout.user.base :refer [base]]
            [eat.layout.components :refer [sticky-footer-fix]]))

(defn privacy-content []
  [:div 
   [:p "We do not share personal information with third-parties. We do not store information we collect about your visit to this blog, other than to analyze content performance through the use of cookies. Please note: you may, at anytime, disable the use of cookies by modifying your internet browser's settings."]
   [:p "We are not responsible for the republishing of the content found on this blog, on other web sites or media, without our permission."]
   [:p "This privacy policy is subject to change without notice."]])

(defn disclaimer-main []
  [:div {:class "main"}
   [:h2 "Disclaimer"]
   [:h3 "Privacy Policy"]
   (privacy-content)])

(defn disclaimer-page [layout-config]
  (base layout-config
        {:title "Privacy Policy"
         :content (disclaimer-main)
         :js (sticky-footer-fix)}))
