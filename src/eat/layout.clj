(ns eat.layout
  (:require [hiccup.page :refer [html5]]
            [eat.io :refer [get-resource]]
            [optimus.link :as link]))

(defn render-page [page content request]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1.0"}]
    [:title (:title page)]
    [:link {:rel "stylesheet" :href (link/file-path request "/styles/main.css")}]]
   [:body
     ;;Add body scripts    
    [:div#main.body
     (:body page)]
    [:div#footer
     "This is a footer"]]))
