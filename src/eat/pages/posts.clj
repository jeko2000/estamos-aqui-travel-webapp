(ns eat.pages.posts
  (:require [hiccup.core :refer [html]]
            [me.raynes.cegdown :as md]))

(defn render-post-page [post next]
  (html
   [:div#post
    [:h1 (:title post)]
    [:h4 (:date post)]]
   [:div.content
    (md/to-html (:content post))]
   [:ul
    (when next
      [:li [:a {:href (:url next)} (:title next)]])]))

(defn get-post-page [[post next]]
  [(:url post)
   (assoc-in post [:content] (render-post-page post next))])

(defn get-post-pages [content]
  (->> (:posts content)
       (partition-all 2 1)
       (map #(get-post-page %))
       (into {})))

