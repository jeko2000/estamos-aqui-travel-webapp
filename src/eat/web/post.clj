(ns eat.web.post
  (:require [hiccup.element :refer [link-to]]
            [eat.web.base :refer [base]]))

(defn post-content [post]
  [:article {:class "post-meta"}
   [:h2 (link-to (:url post) (:title post))]
   [:div {:class "row"}
    [:div {:class "post-meta-group-1 col-sm-6 col-md-6"}
     [:span {:class "glyphicon glyphicon-pencil"}]
     (if-let [tg (:tags post)] (clojure.string/join " " tg))]

    [:div {:class "post-meta-group-2 col-sm-6 col-md-6"}
     [:span {:class "glyphicon glyphicon-pencil"}]
     (:date post)]]
   (:content post)])


(defn comment-box []
  [:div {:class "well"}
   [:h4 "Leave a comment"]
   [:form {:role "form" :class "clearfix"}
    [:div {:class "col-md-6 form-group"}
     [:label {:class "sr-only" :for "name"} "Name"]
     [:input {:type "text" :class "form-control" :id "name" :placeholder "Name"}]]
    [:div {:class "col-md-6 form-group"}
     [:label {:class "sr-only" :for "email"} "Email"]
     [:input {:type "email" :class "form-control" :id "email" :placeholder "Email"}]]
    [:div {:class "col-md-12 form-group"}
     [:label {:class "sr-only" :for "comment"} "Comment"]
     [:textarea {:class "form-control" :id "comment" :placeholder "Comment"}]]
    [:div {:class "col-md-12 form-group text-right"}
     [:button {:type "submit" :class "btn btn-primary"} "Submit"]]]])

(defn comments []
  [:ul {:id "comments" :class "comments"}
   [:li {:class "comment"}
    [:div {:class "clearfix"}
     [:h4 {:class "pull-left"} "John"]
     [:p {:class "pull-right"} "9:41 PM on August 24, 2013"]]
    [:p
     [:em "I don't believe in astrology but still your writing style is really great!"]]]
   [:li {:class "comment"}
    [:div {:class "clearfix"}
     [:h4 {:class "pull-left"} "John"]
     [:p {:class "pull-right"} "9:41 PM on August 24, 2013"]]
    [:p
     [:em "I don't believe in astrology but still your writing style is really great!"]]]])

(defn post [curr-post next posts]
  (base {:title (str (:title curr-post) " | Estamos Aqui Travel")
         :content (list
                   (post-content curr-post)
                   [:hr]
                   (comments)
                   (comment-box))
         :posts posts}))
