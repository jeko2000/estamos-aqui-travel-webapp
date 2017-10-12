(ns eat.layout.admin.post
  (:require [eat.layout.admin.base :refer [admin-base admin-modal]]
            [eat.layout.util :refer [set->string record->table-row]]
            [hiccup.element :refer [link-to]]
            [hiccup.form :as f]))

(defn admin-post-form [{:keys [author md date preview preview-img tags title url]}]
  (f/form-to {:enctype "multipart/form-data"} ["POST" "/admin/edit"]

             [:div {:class "form-group"}
              (f/label "images" "Upload Images")
              (f/file-upload {:class "form-control" :multiple "true" :accept="image/*"} "file")]
             
             [:div {:class "form-group"}
              (f/label "title" "Post Title")
              (f/text-field {:placeholder "Top 10 waterloo fails" :class "form-control"} "title" title)]

             [:div {:class "form-group"}
              (f/label "author" "Post Author")
              (f/text-field {:placeholder "Yoda" :class "form-control"} "author" author)]

             [:div {:class "form-group"}
              (f/label "date" "Post Date")
              (f/text-field {:placeholder "2000-01-01" :class "form-control"} "date" date)]

             [:div {:class "form-group"}
              (f/label "content" "Post Content")
              (f/text-area {:placeholder "# Top 10 waterloo fails" :rows "25" :class "form-control"} "content" md)]

             [:div {:class "form-group"}
              (f/label "preview" "Post Preview")
              (f/text-field {:placeholder "Let there be..." :class "form-control"} "preview" preview)]

             [:div {:class "form-group"}
              (f/label "preview-img" "Post Preview Image")
              (f/text-field {:placeholder "img/my-favorite-bench.jpg" :class "form-control"} "preview-img" preview-img)]                   

             [:div {:class "form-group"}
              (f/label "tags" "Meta Tags")
              (f/text-field {:placeholder "franctic, dragonfly, candlelight" :class "form-control"} "tags" (set->string tags))]
             [:div {:class "panel-footer"}
              (f/submit-button {:class "btn btn-primary"} "Save changes")]))


(defn admin-post-main [params]
  [:section {:id "main"}
   [:div {:class "container"}
    [:div {:class "row"}
     [:div {:class "col-md-12"}
      [:div {:class "panel panel-default"}
       [:div {:class "panel-heading"}
        [:h3 {:class "panel-title"} "Post page"]]
       [:hr]
       (admin-post-form params)]]]]])

(defn admin-post-page [layout-config post-map]
  (admin-base layout-config
              {:title "Edit Page"
               :user "Shelly"
               :content (admin-post-main post-map)}))
