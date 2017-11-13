(ns eat.layout.user.author
  (:require [eat.layout.user.base :refer [base previews]]
            [eat.layout.components :refer [sticky-footer-fix]]))

(defn author-page [layout-config author posts-by-author]
  (base layout-config
        {:title (str "Posts by " author)
         :content (previews layout-config posts-by-author (str "Posts by " author))
         :js (sticky-footer-fix)}))
