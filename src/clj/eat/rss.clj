(ns eat.rss
  (:require [clojure.data.xml :as xml]
            [net.cgrand.enlive-html :as enlive]))

(defn- process-img-node [node]
  (let [curr-src (-> node :attrs :src)
        new-node (-> node
                     (update-in [:attrs :src] #(str "https://estamosaquitravel.com" %))
                     (assoc-in [:attrs :width] "100%")
                     (assoc-in [:attrs :height] "auto"))]
    new-node))

(defn update-img-meta [page]
  (enlive/sniptest page
                   [:p :img] process-img-node))

(defn- entry [{:keys [title date author url content]}]
  [:entry
   [:title title]
   [:link {:href (str "https://estamosaquitravel.com" url)}]
   [:id (str "urn:estamosaquitravel.com:feed:post" url)]   
   [:updated date]
   [:author author]
   [:content {:type "html"} (update-img-meta content)]])

(defn atom-feed [posts]
  (xml/emit-str
   (xml/sexp-as-element
    [:feed {:xmlns "http://www.w3.org/2005/Atom"}
     [:title {:type "text"} "Estamos Aqui Travel"]
     #_[:logo "https://estamosaquitravel.com/static/img/logo_large_png.png"]
     [:logo "https://estamosaquitravel.com/static/img/favicon.png"]
     [:summary "Estamos Aqui Travel provides well-crafted images, tips, and recommendations for South American travel"]
     [:updated (java.util.Date.)]
     [:id "urn:estamosaquitravel.com:feed"]
     [:link {:rel "self" :type "application/atom+xml"
             :href "https://estamosaquitravel.com/feed.atom"}]
     [:rights "(c) 2017 Copyright, Estamos Aqui Travel"]
     (map entry posts)])))


