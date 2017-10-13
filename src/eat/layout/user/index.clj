(ns eat.layout.user.index
  (:require [eat.layout.user.base :refer [base previews]]
            [hiccup.element :refer [image]]))

(defn carousel []
  [:div {:id "carousel-header" :class "carousel slide" :data-ride "carousel"}
   [:ol {:class "carousel-indicators"}
    [:li {:data-target "#carousel-header" :data-slide-to "0" :class "active"}]
    [:li {:data-target "#carousel-header" :data-slide-to "1"}]
    [:li {:data-target "#carousel-header" :data-slide-to "2"}]
    [:li {:data-target "#carousel-header" :data-slide-to "3"}]
    [:li {:data-target "#carousel-header" :data-slide-to "4"}]]
   
   [:div {:class "carousel-inner" :role "listbox"}
    [:div {:class "item active"}
     (image {:class "img img-responsive"} "/img/1200x400/carousel_1.jpg" "image1")]    
    [:div {:class "item"}
     (image {:class "img img-responsive"} "/img/1200x400/carousel_2.jpg" "image2")]
    [:div {:class "item"}
     (image {:class "img img-responsive"} "/img/1200x400/carousel_3.jpg" "image3")]    

        [:div {:class "item"}
     (image {:class "img img-responsive"} "/img/1200x400/carousel_4.jpg" "image4")]    

        [:div {:class "item"}
     (image {:class "img img-responsive"} "/img/1200x400/carousel_5.jpg" "image5")]    ]

   [:a {:class "left carousel-control" :href "#carousel-header" :role "button" :data-slide "prev"}
    [:span {:class "glyphicon glyphicon-chevron-left" :aria-hidden "true"}]
    [:span {:class "sr-only"} "Previous"]]
   [:a {:class "right carousel-control" :href "#carousel-header" :role "button" :data-slide "next"}
    [:span {:class "glyphicon glyphicon-chevron-right" :aria-hidden "true"}]
    [:span {:class "sr-only"} "Next"]]])

(defn quick-links []
  [:div {:id "quick-links" :class "site-content"}
   [:div {:class "container top-section"}
    #_[:div {:class "row"}
     [:div {:class "col-md-12"}
      (image {:class "img-responsive center-block"} "img/explore.png" "explore")]]
    #_[:br]
    [:div {:class "row"}
     (repeat 6
             [:div {:class "col-xs-6 col-sm-4 col-md-4"}
              (image {:class "img-thumbnail center-block"} "/img/600x400.png" "about")])]]])

(defn index-page [layout-config posts]
  (base layout-config
        {:title "Estamos Aqui Travel"
         :pre-content (list
                       (carousel)
                       (quick-links))
         :content (list
                   (previews layout-config posts "Recent Posts"))}))
