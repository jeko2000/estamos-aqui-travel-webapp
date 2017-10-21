(ns eat.layout.user.search
  (:require [eat.layout.user.base :refer [base previews]]
            [eat.layout.components :refer [sticky-footer-fix]]))

(defn search-body [layout-config safe-query result-posts]
  [:div {:class "search-results"}
   (previews layout-config result-posts (str "Results for " safe-query))])

(defn search-body-no-results [layout-config safe-query]
  [:div {:class "search-results"}
   [:h2 {:class "text-center text-uppercase subtitle"}
    "No Results for " safe-query]])

(defn search-page [layout-config safe-query result-posts]
  (base layout-config
        {:title "Search Results Page"
         :content (search-body layout-config safe-query result-posts)
         :js (sticky-footer-fix)}))

(defn search-page-no-results [layout-config safe-query]
  (base layout-config
        {:title "Search Results Page"
         :content (search-body-no-results layout-config safe-query)
         :js (sticky-footer-fix)}))
