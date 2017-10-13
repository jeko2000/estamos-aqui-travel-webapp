(ns eat.layout.components
  (:require [hiccup.element :refer [link-to]]
            [clojure.string :as str]))

(defn set->string [source-set]
  (str/join ", " source-set))

(defn string->set [tag-string]
  (-> tag-string
      (str/replace "," " ")
      (str/split #"\s+")
      (set)))

(defn record->table-row [{:keys [title date tags url]}]
  [:tr
   [:td title]
   [:td date]
   [:td (set->string tags)]
   [:td (link-to {:class "btn btn-default"} (str "admin/edit" url) "Edit")
    (link-to {:class "btn btn-danger"} (str "admin/delete" url) "Delete")]])
