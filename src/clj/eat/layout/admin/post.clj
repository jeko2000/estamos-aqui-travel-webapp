(ns eat.layout.admin.post
  (:require [eat.layout.admin.base :refer [admin-base]]
            [eat.layout.components :refer [set->string record->table-row yes-no-modal validation-error]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.form :as f]
            [hiccup.element :refer [link-to]]))

(defn admin-post-form [post-to modal-id {:keys [id author md date preview preview_img title_img tags title url errors] :as params}]
  (let [data-target (str "#" modal-id)]
    (f/form-to {:enctype "multipart/form-data"} [:post post-to]
               (anti-forgery-field)
               [:div {:class "form-group"}
                (f/label "images" "Upload Images")
                (f/file-upload {:multiple "true" :accept="image/*"} "file")]

               (if id
                 (f/hidden-field  "id" id))

               [:div {:class "form-group"}
                (f/label "title" "Post Title")
                (f/text-field {:placeholder "Top 10 waterloo fails" :class "form-control"} "title" title)]

               (validation-error (:title errors))

               [:div {:class "form-group"}
                (f/label "author" "Post Author")
                (f/text-field {:placeholder "Yoda" :class "form-control"} "author" author)]

               (validation-error (:author errors))

               [:div {:class "form-group"}
                (f/label "date" "Post Date")
                (f/text-field {:placeholder "2000-01-01" :class "form-control"} "date" date)]

               (validation-error (:date errors))

               [:div {:class "form-group"}
                (f/label "content" "Post Content")
                (f/text-area {:placeholder "# Top 10 waterloo fails" :rows "25" :class "form-control"} "md" md)]

               (validation-error (:md errors))

               [:div {:class "form-group"}
                (f/label "preview" "Post Preview")
                (f/text-field {:placeholder "Let there be..." :class "form-control"} "preview" preview)]

               (validation-error (:preview errors))

               [:div {:class "form-group"}
                (f/label "preview_img" "Post Preview Image")
                (f/text-field {:placeholder "img/my-favorite-bench.jpg" :class "form-control"} "preview_img" preview_img)]

               (validation-error (:preview_img errors))

               [:div {:class "form-group"}
                (f/label "title_img" "Post Title Image")
                (f/text-field {:placeholder "img/my-favorite-bench.jpg" :class "form-control"} "title_img" title_img)]

               (validation-error (:title_img errors))

               [:div {:class "form-group"}
                (f/label "tags" "Meta Tags")
                (f/text-field {:placeholder "franctic, dragonfly, candlelight" :class "form-control"} "tags" (set->string tags))]

               (validation-error (:tags errors))

               [:div {:class "panel-footer"}
                (if id [:button {:type "button" :class "btn btn-danger" :data-toggle "modal" :data-target data-target} "Delete"])
                (f/submit-button {:class "btn btn-primary pull-right"} "Save changes")
                (link-to {:class "btn btn-default pull-right"} "/admin" "Cancel")])))

(defn admin-post-main [post-to modal-id params]
  [:div {:class "row"}
   [:div {:class "col-md-12"}
    [:div {:class "panel panel-default"}
     [:div {:class "panel-heading"}
      [:h3 {:class "panel-title"} "Post page"]]
     [:hr]
     (admin-post-form post-to modal-id params)]]])

(defn admin-post-page [layout-config post-to post-map]
  (let [modal-id "delete-post-modal"]
    (admin-base layout-config
                {:title "Edit Page"
                 :user "Shelly"
                 :content (list (admin-post-main post-to modal-id post-map)
                                (yes-no-modal modal-id "Are you sure?" "/admin/delete-post"))})))
