(ns eat.layout.admin.base
  (:require [eat.layout.base :refer [head-tag]]
            [eat.layout.util :refer [set->string]]
            [hiccup.element :refer [unordered-list image link-to]]
            [hiccup.core :refer [html]]
            [clojure.string :as str]))

(defn admin-nav [user]
  [:header {:class "navbar navbar-default navbar-fixed-top" :role "banner"}
   [:div {:class "container"}
    [:div {:class "navbar-header"}
     [:button {:type "button" :class "navbar-toggle collapsed" :data-toggle "collapse" :data-target "#navbar" :aria-expanded "false" :aria-controls "navbar"}
      [:span {:class "sr-only"} "Toggle navigation"]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]]
     #_(link-to {:class "navbar-brand"} "/" "ESTAMOS AQUI TRAVEL")
     (link-to {:class "navbar-brand"} "/"
              (image {:height "25"} "https://mdbootstrap.com/img/logo/mdb-transparent.png"))]
    
    [:div {:id "navbar" :class "collapse navbar-collapse" :role "navigation"}
     #_(unordered-list {:class "nav navbar-nav"}
                     [(link-to "admin/main" "MAIN")
                      (link-to "admin/posts" "POSTS")])
     (unordered-list {:class "nav navbar-nav navbar-right"}
                     [(link-to {:class "text-uppercase"} "admin/main" (str "Welcome, " user))])]]])

(defn admin-header []
  [:header {:id "header"}
   [:div {:class "container"}
    [:div {:class "row"}
     [:div {:class "col-md-10"}
      [:h1 " " 
       [:span {:class "glyphicon glyphicon-cog"}]" Dashboard " 
       [:small "Manage Your Site"]]]
     [:div {:class "col-md-2"}
      [:div {:class "dropdown create"}
       [:button {:class "btn btn-default dropdown-toggle", :type "button", :id "dropdownMenu1", :data-toggle "dropdown", :aria-haspopup "true", :aria-expanded "true"} "Create Content" 
        [:span {:class "caret"}]]
       [:ul {:class "dropdown-menu", :aria-labelledby "dropdownMenu1"}
        [:li 
         [:a {:type "button", :data-toggle "modal", :data-target "#post-modal"} "Add Page"]]  
        [:li 
         [:a {:href "#"} "Add Post"]]
        [:li 
         [:a {:href "#"} "Add User"]]]]]]]])


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

#_(defn admin-page []
  (html
   [:html
    (head-tag "Admin")]
   [:body
    (admin-nav "Johnny")]
   (admin-header)
   (admin-breadcrum)
   [:div {:class "content-wrapper"}
    [:div {:class "container"}
     [:div {:class "row"}
      [:div {:id "primary" :class "col-md-12"}
       (admin-main)
       (admin-modal)
       (footer)]]]]))


(defn admin-base [layout-config {:keys [title pre-content content posts user]}]
  (html
   [:html
    (head-tag title)
    [:body
     (admin-nav user)
     (admin-header)
     pre-content
     [:div {:class "content-wrapper"}
      [:div {:class "container"}
       [:div {:class "row"}
        [:div {:id "primary" :class "col-md-12"}
         content]]]]]]))




#_(defn admin-page [layout-config posts]
  (base layout-config
        {:title "Admin"
         :navigation (admin-nav "Johnny")
         :pre-content (list
                       (admin-header)
                       #_(admin-breadcrum))
         :content (list
                   (admin-main posts)
       (admin-modal))}))

#_(defn admin-page []
  (html
   [:html
    (head-tag "Admin")]
   [:body
    (admin-nav "Johnny")]
   (admin-header)
   (admin-breadcrum)
   [:div {:class "content-wrapper"}
    [:div {:class "container"}
     [:div {:class "row"}
      [:div {:id "primary" :class "col-md-12"}
       (admin-main)
       (admin-modal)
       (footer)]]]]))
