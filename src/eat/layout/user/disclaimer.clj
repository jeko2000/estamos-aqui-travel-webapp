(ns eat.layout.user.disclaimer
  (:require [eat.layout.user.base :refer [base]]))

(defn privacy-content []
  [:div 
   [:p "We do not share personal information with third-parties nor do we store information we collect about your visit to this blog for use other than to analyze content performance through the use of cookies. Please note you may, at anytime, disable the user of cookies by modifying your Internet browser's settings."]
   [:p "We are not responsible for the republishing of the content found on this blog on other Web sites or media without our permission."]
   [:p "This privacy policy is subject to change without notice."]])

(defn disclaimer-main []
  [:div {:class "main"}
   [:h2 "Disclaimer"]
   [:h3 "Privacy Policy"]
   (privacy-content)])

(defn disclaimer-page [layout-config]
  (base layout-config
        {:title "Privacy Policy"
         :content (disclaimer-main)}))
