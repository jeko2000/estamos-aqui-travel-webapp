(ns eat.layout.user.all-posts
  (:require [eat.layout.user.base :refer [base previews]]
            [eat.layout.components :refer [sticky-footer-fix]]))

(defn all-posts-page [layout-config posts]
  (base layout-config
        {:title "All Posts"
         :content (previews layout-config posts "All posts")
         :js (sticky-footer-fix)}))
