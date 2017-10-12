(ns eat.layout.user.base
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [include-css include-js]]
            [hiccup.element :refer [unordered-list image link-to]]
            [eat.layout.util :refer [urlize-tag]]))

(defn head-tag [title]
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:meta {:name "description"
           :content "Estamos Aqui Travel provides well-crafted images, tips, and recommendations South American travel."}]
   [:title title]
   (include-css "/css/bootstrap.min.css" "/css/style.css")])

(defn navbar []
  [:header {:class "navbar navbar-default navbar-fixed-top" :role "banner"}
   [:div {:class "container"}
    [:div {:class "navbar-header"}
     [:button {:type "button" :class "navbar-toggle collapsed" :data-toggle "collapse" :data-target "#navbar" :aria-expanded "false" :aria-controls "navbar"}
      [:span {:class "sr-only"} "Toggle navigation"]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]]
     #_(link-to {:class "navbar-brand"} "/" "ESTAMOS AQUI TRAVEL")
     (link-to {:class "navbar-brand"} "/"
              (image {:height "25"} "https://mdbootstrap.com/img/logo/mdb-transparent.png"))
     ]
    
    [:div {:id "navbar" :class "collapse navbar-collapse" :role "navigation"}
     #_(unordered-list {:class "nav navbar-nav"}
                     [(link-to "/" "Home")
                      #_(link-to "destinations.html" "Destinations")
                      #_(link-to "photography.html" "Photography")])
     (unordered-list {:class "nav navbar-nav navbar-right"}                     
                     [(link-to "/" "HOME")
                      (link-to "about-us.html" "ABOUT")
                      #_(link-to "contact.html" "Contact")])]]])

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
                         (map #(link-to (urlize-tag % tags-output-prefix) %) tags))]])]))

(defn footer []
  [:footer {:class "footer"}
   [:div {:class "container"}
    [:hr]
    [:p {:class "text-center"} ;;TODO: Add footer links!
     "Copyright &copy; 2017"]
    [:p "Upcoming link"]]])

(defn body-js []
  [:div
   (include-js "https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"
               "/js/bootstrap.min.js")])

#_(defn- preview-post [{:keys [tags-output-prefix]} {:keys [url title tags preview preview-img date author]}]
  [:article {:class "post-meta"}
   [:h2 {:class "text-center"} (link-to url title)]
   [:div {:class "row"}
    [:div {:class "post-meta-group-1 col-sm-12 col-md-12 text-center"}
     (unordered-list {:class "list-inline center-block"}
                     (conj 
                      (map #(link-to (urlize-tag % tags-output-prefix) %) tags)
                      [:span {:class "glyphicon glyphicon-pencil"}]))]]   
   (if preview-img
     (link-to url
              (image {:class "img-thumbnail"} preview-img)))
   [:div {:class "row"}
    [:div {:class "post-meta-group-1 col-sm-9 col-md-9"}
     [:span {:class "glyphicon glyphicon-pencil"}]
     " " author]
    [:div {:class "post-meta-group-2 col-sm-3 col-md-3"}
     [:span {:class "glyphicon glyphicon-time"}] " "
     date]]
   [:p
    preview]
   [:div {:class "read-more"}
    [:br]
    (link-to url "Read More")]])

#_(defn- preview-post [{:keys [tags-output-prefix]} {:keys [url title tags preview preview-img date]}]
  [:article {:class "post-meta"}
   [:h2 (link-to url title)]
   [:div {:class "row"}
    [:div {:class "post-meta-group-1 col-sm-6 col-md-6"}
     (unordered-list {:class "list-inline"}
                     (conj 
                      (map #(link-to (urlize-tag % tags-output-prefix) %) tags)
                      [:span {:class "glyphicon glyphicon-pencil"}]))]
    [:div {:class "post-meta-group-2 col-sm-6 col-md-6"}
     [:span {:class "glyphicon glyphicon-time"}] " "
     date]]
   (if preview-img
     (link-to url
              (image {:class "img-responsive center-block"} preview-img)))
   [:p
    preview]
   [:div {:class "read-more"}
    [:br]
    (link-to url "Read More")]])

#_(defn- preview-post [{:keys [tags-output-prefix]} {:keys [url title tags preview preview-img date author]}]
  [:article
   [:h2 [:a {:href url} (clojure.string/upper-case title)] ]
   [:div {:class "post-meta"}
      [:div {:class "row"}
       [:div {:class "col-sm-9 col-md-9"}
        [:div {:class "post-meta-group-1"}
         (unordered-list {:class "list-inline"}
                         (conj 
                          (map #(link-to (urlize-tag % tags-output-prefix) %) tags)
                          [:span {:class "glyphicon glyphicon-tags"}]))]]
       [:div {:class "post-meta-group-2 col-sm-3 col-md-3"}
        [:span {:class "glyphicon glyphicon-time"}] " "
        date]]]
   [:div {:class "row"}
    [:div {:class "col-sm-4 col-md-4"}
      (if preview-img
        (link-to url
                 (image {:class "img-responsive"} preview-img)))]
    
    [:div {:class "col-sm-8 col-md-8"}          
     [:p
      preview
      " " [:span {:class "glyphicon glyphicon-pencil"}]
     " " author]]]
   [:div {:class "read-more"}
    [:br]
    (link-to url "Read More")]])

(defn- preview-post [{:keys [tags-output-prefix]} {:keys [url title tags preview preview-img date author]}]
  [:article {:class "preview-container col-md-4 col-sm-4 col-xs-4 text-center center-block"}
   (if preview-img
     (link-to url
              (image {:class "img-responsive img-preview"} preview-img)))
   [:h3 {:class "h4 text-uppercase"} (link-to url title)]
   [:p {:class "preview-desc"}
    preview]
   [:div {:class "preview-bottom"}
    #_[:div {:class "read-more"}
     [:br]
     (link-to  url "Read More")]]])

(defn- display-post-pair [layout-config [post-left & [post-mid post-right]]]
  [:div {:class "container"}
   [:div {:class "row"}
    (preview-post layout-config post-left)
    (if post-mid (preview-post layout-config post-mid))
    (if post-right (preview-post layout-config post-right))    ]
   [:hr]])
   
(defn previews [layout-config posts heading]
  [:div {:id "post-previews"}
   [:h3 {:class "text-center text-uppercase"} heading]
   [:hr]
   [:div {:class "content"}
    [:div {:class "row display-flex"}
     (map #(display-post-pair layout-config %)
          (partition-all 3 posts))]]
   #_[:ul {:class "pager"}
    [:li {:class "previous"}
     [:a {:href "#"} "<- Older"]]
    [:li {:class "next"}
     [:a {:href "#"} "Next ->"]]]])

(defn base [layout-config {:keys [title pre-content content posts tags]}]
  (let [col-width (if (or posts tags) 8 12)]
    (html
     [:html
      (head-tag title)
      [:body
       (navbar)
       pre-content
       [:div {:class "content-wrapper"}
        [:div {:class "container"}
         [:div {:class "row"}
          [:div {:id "primary" :class (str "col-md-" col-width)}
           content]
          (sidebar layout-config posts tags)]]]
       (footer)
       (body-js)]])))
