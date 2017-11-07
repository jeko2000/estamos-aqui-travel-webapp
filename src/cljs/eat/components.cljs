(ns eat.components)

(def sample-ins [{:url "https://www.instagram.com/p/Ba2gNNdg16S/", :resolution "https://scontent.cdninstagram.com/t51.2885-15/s150x150/e35/c180.0.720.720/23099024_133730367201933_9198507685199216640_n.jpg"} {:url "https://www.instagram.com/p/Ba2elWdg9ph/", :resolution "https://scontent.cdninstagram.com/t51.2885-15/s150x150/e35/c180.0.720.720/22858187_1550895724957104_4475714311683047424_n.jpg"} {:url "https://www.instagram.com/p/Ba2c6Y3grlN/", :resolution "https://scontent.cdninstagram.com/t51.2885-15/s150x150/e35/c164.0.751.751/22802693_2077890809105930_1943705209494568960_n.jpg"} {:url "https://www.instagram.com/p/Ba2aIlQgau0/", :resolution "https://scontent.cdninstagram.com/t51.2885-15/s150x150/e35/c180.0.720.720/23101069_1439384592847493_3250813544651816960_n.jpg"} {:url "https://www.instagram.com/p/Ba2ZGmgAA_q/", :resolution "https://scontent.cdninstagram.com/t51.2885-15/s150x150/e35/c180.0.720.720/22802575_144657822933192_2809682246863159296_n.jpg"} {:url "https://www.instagram.com/p/BawNxSqAh0N/", :resolution "https://scontent.cdninstagram.com/t51.2885-15/s150x150/e35/22794446_131885180866080_8060726198008807424_n.jpg"}])

(defn image
  ([src] (image {} src nil))
  ([src alt] (image {} src alt))
  ([attr-map src alt] [:img (merge attr-map {:src src :alt alt})]))

(defn link-to
  ([url content] (link-to {} url content))
  ([attr-map url content] [:a (merge attr-map {:href url}) content]))

(defn external-image-gallery [images]
  [:div {:class "image-gallery"}
   (map (fn [{:keys [url resolution]}]
          (link-to url
                   (image {:class "img col-md-2 col-sm-3 col-xs-6"} resolution "instagram-pic"))) images)])

(defn instagram-gallery [instagram-images]
  (if instagram-images
    [:div {:class "gallery-header"}
     [:h2 {:class "text-center text-uppercase subtitle"} "Follow us on Instagram"]
     [:br]
     [:div {:class "gallery-body"}
      #_[:h2 "sampled"]
      (external-image-gallery instagram-images)]]))

