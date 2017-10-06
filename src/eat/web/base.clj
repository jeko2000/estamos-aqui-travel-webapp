(ns eat.web.base
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [include-css include-js]]
            [hiccup.element :refer [unordered-list image link-to]]
            [eat.web.util :refer [urlize-tag]]))

(def sidebar-latest-post-count 3) ;;Add to config

(defn head-tag [title]
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:meta {:name "description"
           :content "Estamos Aqui Travel provides well-crafted images, tips, and recommendations South American travel."}]
   [:title title]
   (include-css "/css/bootstrap.min.css" "/css/style.css")])

(defn navigation []
  [:header {:class "navbar navbar-default navbar-fixed-top" :role "banner"}
   [:div {:class "container"}
    [:div {:class "navbar-header"}
     [:button {:type "button" :class "navbar-toggle collapsed" :data-toggle "collapse" :data-target "#navbar" :aria-expanded "false" :aria-controls "navbar"}
      [:span {:class "sr-only"} "Toggle navigation"]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]]
     (link-to {:class "navbar-brand"} "/" "EstamosAquiTravel")]
    [:div {:id "navbar" :class "collapse navbar-collapse" :role "navigation"}
     (unordered-list {:class "nav navbar-nav"}
                     [(link-to "/" "Home")
                      (link-to "destinations.html" "Destinations")
                      (link-to "photography.html" "Photography")])
     (unordered-list {:class "nav navbar-nav navbar-right"}
                     [(link-to "about-us.html" "About Us")
                      (link-to "contact.html" "Contact")])]]])

(defn sidebar [posts tags]
  (let [latest (take sidebar-latest-post-count posts)]
    [:div {:id "secondary" :class "col-md-4" :role "complementary"}
     (if posts
       [:div {:class "panel panel-default"}
        [:div {:class "panel-heading"}
         [:h4 "Latest Posts"]]
        [:div {:class "panel-body"}
         [:ul {:class "list-group"}
          (for [p latest]
            [:li {:class "list-group-item"}
             [:a {:href (:url p)} (:title p)]])]]])
     (if tags
       [:div {:class "panel panel-default"}
        [:div {:class "panel-heading"}
         [:h4 "Tags"]]
        [:div {:class "panel-body"}
         (unordered-list {:class "list-inline"}
                         (map #(link-to (urlize-tag %) %) tags))]])]))

(defn footer []
  [:footer {:class "footer"}
   [:div {:class "container"}
    [:hr]
    [:p {:class "text-center"} ;;TODO: Add footer links!
     "Copyright &copy; 2017"]]])

(defn body-js []
  [:div
   (include-js "https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"
               "/js/bootstrap.min.js")])


(defn- preview-post [{:keys [url title tags preview preview-img date]}]
  [:article {:class "post-meta"}
   [:h2 (link-to url title)]
   [:div {:class "row"}
    [:div {:class "post-meta-group-1 col-sm-6 col-md-6"}
     (unordered-list {:class "list-inline"}
                     (conj 
                      (map #(link-to (urlize-tag %) %) tags)
                      [:span {:class "glyphicon glyphicon-pencil"}]))]
    [:div {:class "post-meta-group-2 col-sm-6 col-md-6"}
     [:span {:class "glyphicon glyphicon-time"}] " "
     date]]
   (if preview-img
     (link-to url
              (image {:class "img-responsive"} preview-img)))
   preview
   [:div {:class "read-more"}
    [:br]
    (link-to url "Read More")]])

(defn previews [posts heading]
  [:div {:id "post-previews"}
   [:h1 heading]
   (interpose [:hr]
              (map preview-post posts))
   [:ul {:class "pager"}
    [:li {:class "previous"}
     [:a {:href "#"} "<- Older"]]
    [:li {:class "next"}
     [:a {:href "#"} "Next ->"]]]])


(defn base [{:keys [title pre-content content posts tags]}]
  (html
   [:html
    (head-tag title)
    [:body
     (navigation)
     pre-content
     [:div {:class "container"}
      [:div {:class "row"}
       [:div {:id "primary" :class "col-md-8"}
        content]
       
       (sidebar posts tags)]]
     (footer)]]))
