(ns eat.layout.user.search
  (:require [eat.layout.user.base :refer [base previews]]
            [eat.search :refer [search]]))

(defn search-body [layout-config safe-query]
  [:div {:class "search-results"}
   (if-let [result-posts (search safe-query)]
     (previews layout-config result-posts (str "Results for " safe-query))
     [:h2 {:class "text-center text-uppercase subtitle"}
      "No Results for " safe-query])])

(defn search-page [layout-config safe-query]
  (base layout-config
        {:title "Search Results Page"
         :content (search-body layout-config safe-query)}))
