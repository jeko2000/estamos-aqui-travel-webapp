(ns eat.layout.user.index
  (:require [eat.layout.user.base :refer [base previews]]
            [eat.api :refer [instagram-get-recent-images]]
            [eat.layout.components :refer [static-image external-image-gallery]]))

(defn carousel []
  [:div {:id "sub-header"}
   [:div {:id "carousel-header" :class "carousel slide" :data-ride "carousel"}
    [:ol {:class "carousel-indicators"}
     [:li {:data-target "#carousel-header" :data-slide-to "0" :class "active"}]
     [:li {:data-target "#carousel-header" :data-slide-to "1"}]
     [:li {:data-target "#carousel-header" :data-slide-to "2"}]
     [:li {:data-target "#carousel-header" :data-slide-to "3"}]
     [:li {:data-target "#carousel-header" :data-slide-to "4"}]]

    [:div {:class "carousel-inner" :role "listbox"}
     [:div {:class "overlay overlay-sm"}
      (static-image {:class "img img-responsive"} "img/logo_large_png.png" "large-logo")]

     [:div {:class "item active"}
      (static-image {:class "img img-responsive"} "img/carousel_1.jpg" "carousel_1")]
     [:div {:class "item"}
      (static-image {:class "img img-responsive"} "img/carousel_2.jpg" "carousel_2")]
     [:div {:class "item"}
      (static-image {:class "img img-responsive"} "img/carousel_3.jpg" "carousel_3")]

     [:div {:class "item"}
      (static-image {:class "img img-responsive"} "img/carousel_4.jpg" "carousel_4")]

     [:div {:class "item"}
      (static-image {:class "img img-responsive"} "img/carousel_5.jpg" "carousel_5")]    ]

    [:a {:class "left carousel-control" :href "#carousel-header" :role "button" :data-slide "prev"}
     [:span {:class "glyphicon glyphicon-chevron-left" :aria-hidden "true"}]
     [:span {:class "sr-only"} "Previous"]]
    [:a {:class "right carousel-control" :href "#carousel-header" :role "button" :data-slide "next"}
     [:span {:class "glyphicon glyphicon-chevron-right" :aria-hidden "true"}]
     [:span {:class "sr-only"} "Next"]]]])

(defn quick-links []
  [:div {:id "quick-links" :class "site-content"}
   [:div {:class "container top-section"}
    #_[:div {:class "row"}
       [:div {:class "col-md-12"}
        (image {:class "img-responsive center-block"} "img/explore.png" "explore")]]
    #_[:br]
    [:div {:class "row"}
     #_(repeat 6
               [:div {:class "col-xs-6 col-sm-4 col-md-4"}
                (image {:class "img-thumbnail center-block"} "https://dummyimage.com/600x400/000/fff" "about")])]]])


(defn instagram-gallery [count res-key]
  (let [instagram-images (instagram-get-recent-images count res-key)]
    [:div {:class "gallery-header"}
     [:h2 {:class "text-center text-uppercase subtitle"} "Follow us on Instagram"]
     [:br]
     [:div {:class "gallery-body"}
      (external-image-gallery instagram-images)]]))

(defn index-page [layout-config posts]
  (let [latest-posts-count (:index-latest-posts-count layout-config 6)
        latest-posts (take latest-posts-count posts)]
    (base layout-config
          {:title "Estamos Aqui Travel"
           :pre-content (list
                         (carousel)
                         (quick-links))
           :content (list
                     (previews layout-config latest-posts "Recent Posts")
                     (instagram-gallery 6 :thumbnail))})))
