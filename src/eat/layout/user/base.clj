(ns eat.layout.user.base
  (:require [eat.util :refer [tag->uri]]
            [eat.layout.components :refer [hr static-image string->static-uri]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [include-css include-js]]
            [hiccup.form :as f]
            [hiccup.element :refer [unordered-list link-to]]))

(def github-link "https://github.com/jeko2000/estamos-aqui-travel-webapp")
(def instagram-link "https://www.instagram.com/estamosaqui_travel")

(defn head-tag [title]
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:meta {:name "description"
           :content "Estamos Aqui Travel provides well-crafted images, tips, and recommendations South American travel."}]
   [:title title]
   [:link {:rel "shortcut icon" :type "image/png" :href "/static/img/favicon.png"}]
   ;;cdn
   (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
                "https://fonts.googleapis.com/css?family=Roboto")

   ;;required locals
   (include-css (string->static-uri "/css/style.css")
                (string->static-uri "/css/font-awesome-4.7.0/css/font-awesome.min.css"))

   ;;local
   ;;(include-css (string->static-uri "/css/bootstrap.min.css"))
   ])

(defn search-box []
  [:form {:class "navbar-form navbar-left"}
   [:div {:class "input-group"}
    [:input {:class "form-control", :placeholder "Search", :type "text"}]
    [:div {:class "input-group-btn"}
     [:button {:class "btn btn-default", :type "submit"}
      [:i {:class "glyphicon glyphicon-search"}]]]]] )


(defn navbar []
  [:header {:class "navbar navbar-default navbar-fixed-top" :role "banner"}
   [:div {:class "container"
          :id "header"}
    [:div {:class "navbar-header"}
     [:button {:type "button" :class "navbar-toggle collapsed" :data-toggle "collapse" :data-target "#navbar" :aria-expanded "false" :aria-controls "navbar"}
      [:span {:class "sr-only"} "Toggle navigation"]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]]
     (link-to {:class "navbar-brand"} "/"
                (static-image "img/navlogo_color.png" "navigation-logo"))]
    
    [:div {:id "navbar" :class "collapse navbar-collapse" :role "navigation"}
     (unordered-list {:class "nav navbar-nav"}
                     [(link-to "/" "Home")
                      (link-to "/about-us" "About")])
     (unordered-list {:class "nav navbar-nav navbar-right"}                     
                     [#_(link-to "/login" [:span {:class "glyphicon glyphicon-log-in"}] " Login")
                      (search-box)])]]])

(defn sidebar [{:keys [sidebar-latest-post-count tags-output-prefix]} posts tags]
  (let [latest (take sidebar-latest-post-count posts)]
    [:div {:id "secondary" :class "col-md-4" :role "complementary"}
     (if posts
       [:div {:class "panel panel-default"}
        [:div {:class "panel-heading"}
         [:h4 "Recent Posts"]]
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
                         (map #(link-to (tag->uri % tags-output-prefix) %) tags))]])]))

(defn footer []
  [:footer {:class "footer" :id "footer"}
   [:div {:class "container navbar-b"}
    [:div {:class "row"}
     [:div {:class "col-sm-3"}
      [:h2
       [:div {:class "logo-wrap"}
        (link-to "/"
                 (static-image {:class "img img-resonsive"} "/img/logo_large_png_360.png" "large-footer-logo"))]]]
     [:div {:class "col-sm-2"}
      [:h5 "Get started"]
      (unordered-list 
       [(link-to "/" "Home")
        (link-to "/login" "Login")])]
     [:div {:class "col-sm-2"}
      [:h5 "About"]
      (unordered-list 
       [(link-to "/about-us" "About Us")])]
     [:div {:class "col-sm-2"}
      [:h5 "Support"]
      (unordered-list 
       [(link-to "/disclaimer" "Disclaimer")
        (link-to github-link "Source code")])]
     [:div {:class "col-sm-2"}
      [:div {:class "social-networks"}
       (link-to {:class "instagram"}
                instagram-link [:i {:class "fa fa-instagram"}])
       (link-to {:class "github"}
                github-link [:i {:class "fa fa-github"}])]]]]
   [:div {:class "footer-copyright"}
    [:p "© 2017 Copyright Estamos Aqui Travel"]]])

(defn body-js []
  [:div
   (include-js "https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"
               (string->static-uri "/js/bootstrap.min.js"))])

(defn- preview-post [{:keys [tags-output-prefix]} {:keys [url title tags preview preview_img date author]}]
  [:article {:class "preview-container col-md-4 col-sm-6 col-xs-12 text-center center-block"}
   (if preview_img
     (link-to url
              (static-image {:class "img-responsive img-preview"} preview_img "preview-img")))
   [:h3 {:class "preview-title"} (link-to url title)]
   [:p {:class "preview-desc"}
    preview]
   [:div {:class "preview-bottom"}
    #_[:div {:class "read-more"}
       [:br]
       (link-to  url "Read More")]]])

#_(defn- display-post-group [layout-config [post-left & [post-mid post-right]]]
  [:div {:class "container"}
   [:div {:class "row"}
    (preview-post layout-config post-left)
    (if post-mid (preview-post layout-config post-mid))
    (if post-right (preview-post layout-config post-right))]])


#_(defn display-post-groups [layout-config posts]
  (interpose (hr)
             (map #(display-post-group layout-config %)
                  (partition-all 3 posts)))) ;;Add to config

(defn previews [layout-config posts heading]
  [:div {:id "post-previews"}
   [:h2 {:class "text-center text-uppercase subtitle"} heading]
   [:br]
   [:div {:class "content"}
    [:div {:class "row display-flex"}
     (map #(preview-post layout-config %) posts)]]
   #_[:ul {:class "pager"}
      [:li {:class "previous"}
       [:a {:href "#"} "<- Older"]]
      [:li {:class "next"}
       [:a {:href "#"} "Next ->"]]]])

(defn base [layout-config {:keys [title pre-content content posts tags js]}]
  (let [col-width (if (or posts tags) 8 12)]
    (html
     [:html
      (head-tag title)
      [:body
       [:div {:class "wrapper"}
        (navbar)
        pre-content
        [:div {:class "content" :id "content"}
         [:div {:class "container"}
          [:div {:class "row"}
           [:div {:id "primary" :class (str "col-md-" col-width)}
            content]
           (sidebar layout-config posts tags)]]]
        (footer)
        (body-js)
        js]]])))
