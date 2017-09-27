(ns eat.pages.index
  (:require [hiccup.core :refer [html]]))

(defn get-post-link [post]
  (:url post))

(defn- render-post [post]
  (html
   [:li
    [:a {:href (get-post-link post)} (:title post)]]))

(defn- render-index-page [content]
  (html
   [:h2 "Welcome to our blog!"]
   [:div.post-list
    [:ul
     (map render-post (reverse (:posts content)))]]))

(defn get-index-page [content]
  {"/"
   {:title "Estamos Aqui Travel"
    :body (render-index-page content)}})
