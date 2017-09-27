(ns eat.pages.posts
  (:require [hiccup.core :refer [html]]
            [me.raynes.cegdown :as md]            
            [eat.io :refer [get-resource]]
            [eat.pages.index :refer [get-post-link]]))

(defn render-post-page [content post next]
  (html
   [:div#post
    [:h1 (:title post)]
    [:h4 (:date post)]]
   [:div.content
    (md/to-html (get-resource (:file post)))]
   [:ul
    (when next
      [:li [:a {:href (get-post-link next)} "Next Post"]])]))

(defn get-post-page [content [post next]]
  [(get-post-link post) ;;
   {:title (:title post)
    :body (render-post-page content post next)}])

(defn get-post-pages [content]
  (->> (:posts content)
       (partition-all 2 1)
       (map #(get-post-page content %))
       (into {})))

