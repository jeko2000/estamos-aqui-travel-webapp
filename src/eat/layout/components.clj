(ns eat.layout.components
  (:require [hiccup.element :refer [link-to image]]
            [hiccup.form :as f]
            [clojure.string :as str]))

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

(defn sticky-footer-fix []
  [:script
   "$(document).ready(function(){var pageHeight = $('body').height() + $('#header').height(),vpHeight = $(window).height();$('#footer').css('margin-top',parseInt($('#footer').css('margin-top'),10) + vpHeight - pageHeight);});"])

(defn hr []
  (image {:class "img img-responsive"} "/img/divider_small_png.png" "divider"))

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
      (f/form-to ["POST" post-to]
                 (f/submit-button {:class "btn btn-danger"} "Delete post"))]]]])

(defn image-gallery [images]
  [:div {:class "image-gallery"}
   (map (fn [{:keys [url image-res]}]
          (link-to url
                   (image {:class "img col-md-2 col-sm-3 col-xs-6"} image-res "alt"))) images)])

