(ns eat.layout.admin.index
  (:require [eat.layout.admin.base :refer [admin-base]]
            [eat.layout.components :refer [record->table-row]]))


(defn table [records]
  (let [headings (map name (keys (first records)))]
    [:table {:class "table table-hover table-striped"}
     [:tbody 
      [:tr
       (map (fn [heading] [:th {:class "col-md-3"} heading]) headings)
       [:th]]
      (for [record records]
        (record->table-row record))]]))

(defn admin-main [posts]
  (let [reduced-posts (map #(select-keys % [:active :title :date :tags :url]) posts)]
    [:div {:class "row"}
     [:div {:class "col-md-12"}
      [:div {:class "panel panel-default"}
       [:div {:class "panel-heading"}
        [:h4 {:class "panel-title"} "Posts"]]
       [:div {:class "panel-body"}
        (table reduced-posts)]]]]))

(defn admin-page [layout-config posts]
  (admin-base layout-config
              {:title "Admin"
               :user "Shelly"
               :content (list (admin-main posts))
               :create-content? true}))
