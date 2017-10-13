(ns eat.layout.user.post
  (:require [eat.layout.user.base :refer [base]]
            [hiccup.element :refer [unordered-list image]]))

(defn post-header [{:keys [title-img title]}]
  (if title-img
    [:div {:class "post-header"}
     (image {:class "img img-responsive"} title-img title)]))

(defn post-content [{:keys [url title tags date content author preview-img] :as post}] ;;Best so far
  [:div {:class "post-wrapper"}
   [:article
    #_[:h2 {:class "text-uppercase"} title]
    [:div {:class "row"}
     [:div {:class "post-meta"}
      [:div {:class "post-meta-group-1 col-sm-6 col-md-6"}
       (unordered-list {:class "list-inline"}
                       (list                      
                        [:span {:class "glyphicon glyphicon-pencil"}]
                        author
                        ))]
      [:div {:class "post-meta-group-2 col-sm-6 col-md-6"}
       [:span {:class "glyphicon glyphicon-time"}] " "
       date]]]
    [:div {:class "post-content"}
     [:div {:clas "row"}
      [:div {:class "col-md-12"}
       content]]]]])

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

(defn post-page [layout-config curr-post posts]
  (base layout-config
        {:title (str (:title curr-post) " | Estamos Aqui Travel")
         :pre-content (post-header curr-post)
         :content (list
                   (post-content curr-post)
                   [:hr]
                   #_(comments)
                   #_(comment-box))
         :posts posts
         :tags (:tags curr-post)}))
