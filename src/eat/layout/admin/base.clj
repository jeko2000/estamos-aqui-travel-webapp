(ns eat.layout.admin.base
  (:require [eat.layout.user.base :refer [head-tag body-js]]
            [eat.layout.util :refer [set->string]]
            [hiccup.element :refer [unordered-list image link-to]]
            [hiccup.core :refer [html]]
            [hiccup.form :as f]
            [clojure.string :as str]))


(defn create-content-link []
  [:div {:class "dropdown"}
       [:button {:class "btn btn-primary dropdown-toggle"
                 :type "button" :id "new-content-menu"
                 :data-toggle "dropdown" :aria-expanded "false"}
        "Create Content"]
       (unordered-list {:class "dropdown-menu" :role "menu" :aria-labelledby "new-content-menu"}
                       [(link-to "/admin/new-post" "New Post")])])

(defn admin-nav [user]
  [:header {:class "navbar navbar-default navbar-fixed-top" :role "banner"}
   [:div {:class "container"}
    [:div {:class "navbar-header"}
     [:button {:type "button" :class "navbar-toggle collapsed" :data-toggle "collapse" :data-target "#navbar" :aria-expanded "false" :aria-controls "navbar"}
      [:span {:class "sr-only"} "Toggle navigation"]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]]
     (link-to {:class "navbar-brand"} "/"
              (image {:height "25"} "https://mdbootstrap.com/img/logo/mdb-transparent.png"))]
    
    [:div {:id "navbar" :class "collapse navbar-collapse" :role "navigation"}
     (unordered-list {:class "nav navbar-nav navbar-right"}
                     [(link-to {:class "text-uppercase"} "admin/main" (str "Welcome, " user))
                      (f/form-to ["POST" "/logout"]
                                 (f/submit-button {:class "btn btn-primary"} "Logout"))])]]])

(defn admin-header [& [create-content?]]
  [:header {:id "header"}
   [:div {:class "container"}
    [:div {:class "row"}
     [:div {:class "col-md-9"}
      [:h1 " " 
       [:span {:class "glyphicon glyphicon-cog"}]" Dashboard " 
       [:small "Manage Your Site"]]]
     (if create-content?
       [:div {:class "col-md-3 create"} (create-content-link)])]]])


(defn admin-modal []
[:div {:class "modal fade", :id "post-modal", :tabindex "-1", :role "dialog", :aria-labelledby "post-modal-aria"}
 [:div {:class "modal-dialog", :role "document"}
  [:div {:class "modal-content"}
   [:div {:class "modal-header"}
    [:button {:type "button", :class "close", :data-dismiss "modal", :aria-label "Close"}
     [:span {:aria-hidden "true"} "×"]]
    [:h4 {:class "modal-title"} "Add page"]]
   [:div {:class "modal-body"}
    [:form  
     [:div {:class "form-group"}
      [:label "Post Title"]
      [:input {:placeholder "Top 10 waterloo fails", :class "form-control", :type "text"}]]
     
     [:div {:class "form-group"}
      [:label "Post Author"]
      [:input {:placeholder "Yoda", :class "form-control", :type "text"}]]
     
     [:div {:class "form-group"}
      [:label "Post Date"]
      [:input {:placeholder "2000-01-01", :class "form-control", :type "text"}]]

     [:div {:class "form-group"}
      [:label "Post Content"]
      [:textarea {:name "editor1", :class "form-control", :placeholder "# Top 10 waterloo fails"}]]

     #_[:div {:class "checkbox"}
      [:label  
       [:input {:type "checkbox"}]" Published"]]

     [:div {:class "form-group"}
      [:label "Post Preview"]
      [:input {:class "form-control", :placeholder "Let there be...", :type "text"}]]
     
     [:div {:class "form-group"}
      [:label "Post Preview Img"]
      [:input {:class "form-control", :placeholder "img/my-favorite-stool.jpg", :type "text"}]]
     
     [:div {:class "form-group"}
      [:label "Meta Tags"]
      [:input {:class "form-control", :placeholder "frantic, dragonfly, candlelight", :type "text"}]]]]
   
   [:div {:class "modal-footer"}
    [:button {:type "button", :class "btn btn-default", :data-dismiss "modal"} "Close"]
    [:button {:type "button", :class "btn btn-primary"} "Save changes"]]]]])

(defn admin-base [layout-config {:keys [title pre-content content posts user]}]
  (html
   [:html
    (head-tag title)
    [:body
     (admin-nav user)
     (admin-header true)
     pre-content
     [:div {:class "content-wrapper"}
      [:div {:class "container"}
       [:div {:class "row"}
        [:div {:id "primary" :class "col-md-12"}
         content]]]]
     (body-js)]]))

