(ns eat.pages.index
  (:require [hiccup.core :refer [html]]))

(defn- render-post-link [post]
  (html
   [:li
    [:a {:href (:url post)} (:title post)]]))

(defn- render-index-page [content]
  (html
   [:h2 "Welcome to our blog!"]
   [:div.post-list
    [:ul
     (map render-post-link (:posts content))]]))

(defn get-index-page [content]
  {"/"
   {:title "Estamos Aqui Travel"
    :content (render-index-page content)
    :url "/"}})
