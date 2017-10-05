(ns eat.web.base
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [include-css include-js]]
            [hiccup.element :refer [unordered-list ordered-list image link-to]]))

(def max-latest-posts 2) ;;Add to config

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

(defn sidebar [posts]
  (let [latest (take max-latest-posts posts)]
    [:div {:id "secondary" :class "col-md-4" :role "complementary"}
     [:div {:class "panel panel-default"}
      [:div {:class "panel-heading"}
       [:h4 "Latest Posts"]]
      [:div {:class "panel-body"}
       [:ul {:class "list-group"}
        (for [p latest]
          [:li {:class "list-group-item"}
           [:a {:href (:url p)} (:title p)]])]]]

     [:div {:class "panel panel-default"}
      [:div {:class "panel-heading"}
       [:h4 "Tags"]]
      [:div {:class "panel-body"}
       [:ul {:class "list-inline"}
        [:li (link-to "#" "Tag 1")]
        [:li (link-to "#" "Tag 2")]]]]]))

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

(defn base [{:keys [title pre-content content posts]}]
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
       
       (sidebar posts)
       (footer)]]]]))
