(ns eat.exports
  (require [eat.db :refer [find-posts]]
           [eat.db.core :refer [*db*]]
           [clj-pdf.core :refer [pdf template]]))

(defn post-component [{:keys [preview date author md preview_img]}]
  [:paragraph
   [:chunk {:align :justified} (str author " | " date)]
   [:spacer]
   [:chunk {:style :italic} (str " - " preview)] "\n"
   (let [img_file (->> preview_img (str "/var/www/static/") (clojure.java.io/file))]
     (if (.exists img_file)
       [:image (.getPath img_file)]))
   "\n"
   md])

(defn post-pdf [{:keys [title date] :as post} out]
  (pdf
   [{}
    [:heading {:size 10} title]
    [:line]
    (post-component post)]
   out))
