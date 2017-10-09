(ns eat.layout.admin.index
  (:require [eat.layout.admin.base :refer [admin-base admin-modal]]
            [eat.layout.util :refer [set->string record->table-row]]))

#_(defn admin-breadcrum []
    [:section {:id "breadcrumb"}
     [:div {:class "container"}
      [:ol {:class "breadcrumb"}
       [:li {:class "active"} "Dashboard"]]]])

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
  [:section {:id "main"}
   [:div {:class "container"}
    [:div {:class "row"}
     [:div {:class "col-md-12"}
      [:div {:class "panel panel-default"}
       [:div {:class "panel-heading"}
        [:h3 {:class "panel-title"} "Posts"]]
       [:div {:class "panel-body"}
        (table posts)]]]]]])

(defn admin-page [layout-config posts]
  (admin-base layout-config
              {:title "Admin"
               :user "Shelly"
               :content (list (admin-main posts)
                              (admin-modal))}))
