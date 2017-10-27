(ns eat.layout.components
  (:require [eat.config :refer [config]]
            [eat.util :refer [build-path]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.element :refer [link-to image]]
            [hiccup.form :as f]
            [clojure.string :as str]))

(defn string->uri [s]
  (java.net.URI. s))

(defn string->static-uri [s]
  (string->uri (build-path (:static-resource-prefix @config) s)))

(defn set->string [source-set]
  (str/join ", " source-set))

(defn string->set [tag-string]
  (-> tag-string
      (str/replace "," " ")
      (str/split #"\s+")
      (set)))

(defn record->table-row [{:keys [title date tags url]}]
  [:tr
   [:td title]
   [:td date]
   [:td (set->string tags)]
   [:td (link-to {:class "btn btn-primary btn-lg center-block"} (str "admin/edit" url) "Edit")]])

(defn static-image
  ([attr-map src alt]
   [:img (merge attr-map {:src (string->static-uri src) :alt alt})])
  ([src alt]
   (static-image nil src alt)))

(defn external-image-gallery [images]
  [:div {:class "image-gallery"}
   (map (fn [{:keys [url image-res]}]
          (link-to url
                   (image {:class "img col-md-2 col-sm-3 col-xs-6"} image-res "instagram-pic"))) images)])

(defn post-header [{:keys [title_img title]}]
  (if title_img
    [:div {:class "post-header"}
     (static-image {:class "img img-responsive"} title_img title)
     [:div {:class "overlay overlay-lg text-center"}
      [:div {:class "post-title-wrapper"}
       [:h2 {:class "post-title"} title]]]]))

(defn sticky-footer-fix []
  [:script
   "$(document).ready(function(){var pageHeight = $('body').height() + $('#header').height(),vpHeight = $(window).height();$('#footer').css('margin-top',parseInt($('#footer').css('margin-top'),10) + vpHeight - pageHeight);});"])

(defn hr []
  (static-image {:class "img img-responsive"} "/img/divider_small_png.png" "divider"))

(defn yes-no-modal [modal-id modal-text post-to]
  [:div {:class "modal fade" :id modal-id :tabindex "-1" :role "dialog" :aria-labelledby "modal"}
   [:div {:class "modal-dialog" :role "document"}
    [:div {:class "modal-content"}
     [:div {:class "modal-header"}
      [:button {:type "button" :class "close" :data-dismiss "modal" :aria-label "Close"}
       [:span {:aria-hidden "true"} "&times;"]]
      [:h4 {:class "modal-title"} "Admin"]]
     [:div {:class "modal-body"}
      [:p modal-text]]
     [:div {:class "modal-footer"}
      (f/form-to [:post post-to]
                 (anti-forgery-field)
                 (f/submit-button {:class "btn btn-danger"} "Delete post"))]]]])

(defn pager [{:keys [previous-anchor previous-link
                     next-anchor next-link]}]
  [:div {:class "post-pager"}
    [:br]
    [:br]
   [:div {:class "row"}
    [:div {:class "col-md-6 col-sm-6 col-xs-6"}
     [:div {:class "previous-post"}
      (if previous-anchor
        [:div
         [:span "Previous Post"]
         (link-to {:style "display:block;"} previous-link previous-anchor)])]]
    [:div {:class "col-md-6 col-sm-6 col-xs-6"}
     [:div {:class "next-post text-right"}     
      (if next-anchor
        [:div
         [:span "Next Post"]
         (link-to {:style "display:block;"} next-link next-anchor)])]]]])
