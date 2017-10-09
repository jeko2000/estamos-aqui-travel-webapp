(ns eat.layout.admin.edit
  (:require [eat.layout.admin.base :refer [admin-base admin-modal]]
            [eat.layout.util :refer [set->string record->table-row]]
            [hiccup.element :refer [link-to]]
            [hiccup.form :as form]))

(defn edit-main [{:keys [author md date preview preview-img tags title url]}]
  [:section {:id "main"}
   [:div {:class "container"}
    [:div {:class "row"}
     [:div {:class "col-md-12"}
      [:div {:class "panel panel-default"}
       [:div {:class "panel-heading"}
        [:h3 {:class "panel-title"} "Edit page"]]
       [:form  {:method "POST" :action "/admin/edit"}
        [:div {:class "form-group"}
         [:label "Post Title"]
         [:input {:name "title" :placeholder "Top 10 waterloo fails", :class "form-control", :type "text" :value title}]]
        
        [:div {:class "form-group"}
         [:label "Post Author"]
         [:input {:name "author" :placeholder "Yoda", :class "form-control", :type "text" :value author}]]
        
        [:div {:class "form-group"}
         [:label "Post Date"]
         [:input {:name "date" :placeholder "2000-01-01", :class "form-control", :type "text" :value date}]]

        [:div {:class "form-group"}
         [:label "Post Content"]
         [:textarea {:name "content", :class "form-control", :rows "25" :placeholder "# Top 10 waterloo fails"}
          md]]

        [:div {:class "form-group"}
         [:label "Post Preview"]
         [:input {:name "preview" :class "form-control", :placeholder "Let there be...", :type "text" :value preview}]]
        
        [:div {:class "form-group"}
         [:label "Post Preview Img"]
         [:input {:name "preview-img" :class "form-control", :placeholder "img/my-favorite-stool.jpg", :type "text" :value preview-img}]]
        
        [:div {:class "form-group"}
         [:label "Meta Tags"]
         [:input {:name "tags" :class "form-control", :placeholder "frantic, dragonfly, candlelight", :type "text" :value (set->string tags)}]]
        [:div {:class "panel-footer"}
         (link-to {:class "btn btn-default", :data-dismiss "modal"} "/admin" "Close")
         [:input {:type "submit" :class "btn btn-primary" :value "Save changes"}]]]]]]]])

;;Add post

(defn edit-page [layout-config post-map]
  (admin-base layout-config
              {:title "Edit Page"
               :user "Shelly"
               :content (edit-main post-map)}))
