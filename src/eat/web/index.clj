(ns eat.web.index
  (:require [hiccup.element :refer [image link-to]]
            [eat.web.base :refer [base previews]]
            [eat.web.util :refer [get-all-tags]]))

(defn carousel []
  [:div {:id "carousel-header" :class "carousel slide" :data-ride "carousel"}
   [:ol {:class "carousel-indicators"}
    [:li {:data-target "#carousel-header" :data-slide-to "0" :class "active"}]
    [:li {:data-target "#carousel-header" :data-slide-to "1"}]
    [:li {:data-target "#carousel-header" :data-slide-to "2"}]]
   
   [:div {:class "carousel-inner" :role "listbox"}
    [:div {:class "item active"}
     (image {:class "img img-responsive" :width "100%"} "/img/1200x400.png" "image1")
     [:div {:class "carousel-caption"}
      [:h3 "Optional Title"]
      [:p "Optional Subtitle"]]]
    
    [:div {:class "item"}
     (image {:class "img img-responsive" :width "100%"} "/img/1200x400.png" "image1")
     [:div {:class "carousel-caption"}
      [:h3 "Optional Title"]
      [:p "Optional Subtitle"]]]
    
    [:div {:class "item"}
     (image {:class "img img-responsive" :width "100%"} "/img/1200x400.png" "image1")
     [:div {:class "carousel-caption"}
      [:h3 "Optional Title"]
      [:p "Optional Subtitle"]]]]

   [:a {:class "left carousel-control" :href "#carousel-header" :role "button" :data-slide "prev"}
    [:span {:class "glyphicon glyphicon-chevron-left" :aria-hidden "true"}]
    [:span {:class "sr-only"} "Previous"]]
   [:a {:class "right carousel-control" :href "#carousel-header" :role "button" :data-slide "next"}
    [:span {:class "glyphicon glyphicon-chevron-right" :aria-hidden "true"}]
    [:span {:class "sr-only"} "Next"]]])

(defn quick-links []
  [:div {:id "quick-links" :class "site-content"}
   [:div {:class "conteiner top-section"}
    [:div {:class "row"}
     [:div {:class "col-md-12"}
      (image {:class "img-responsive center-block"} "img/explore.png" "explore")]]
    [:br]
    [:div {:class "row"}
     (repeat 4
             [:div {:class "col-xs-6 col-sm-6 col-md-3"}
              [:div {:class "thumbnail"}
               (image "img/square.jpg" "about")]])]]])

(defn index [posts]
  (base {:title "Estamos Aqui Travel"
         :pre-content (list
                       (carousel)
                       [:hr]
                       (quick-links))
         :content (previews posts "Latest Posts!")
         :posts posts
         :tags (get-all-tags posts)}))


(defn index-page [posts]
  {"/" (fn [req] (index posts))})
