(ns eat.layout.util
  (:require [eat.util :refer [build-path]]
            [hiccup.element :refer [link-to]]
            [clojure.string :as str]))

(defn contains-tag? [tag post]
  (if-let [tags (:tags post)]
    (contains? tags tag)))

(defn get-all-tags [posts]
  (->> posts
       (map :tags)
       (apply clojure.set/union)
       (remove nil?)))

(defn urlize-tag [tag tags-output-prefix]
  (as-> tag $
      (clojure.string/replace $ #"\s+" "-")
      (build-path tags-output-prefix $)))

(defn set->string [tag-set]
  (str/join ", " tag-set))

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

