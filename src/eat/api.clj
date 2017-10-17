(ns eat.api
  (:require [eat.config :refer [config]]
            [clj-http.client :as client]))

(def ^:dynamic *instagram-access-token*
  (-> (:instagram-access-token @config)))

(defn- get-recent-media-vector [token count]
  (-> "https://api.instagram.com/v1/users/self/media/recent/?access_token=%s&count=%s"
      (format token count)
      (client/get {:as :json})
      (get-in [:body :data])))

(defn- get-image-url-from-media-item [res-key {:keys [link images]}]
  "The value for RES-KEY must be one of :thumbnail, :low_resolution, or :standard_resolution"
  {:url link
   :image-res (get-in images [res-key :url])})

(defn instagram-get-recent-images [count res-key]
  (-> *instagram-access-token*
      (get-recent-media-vector count)
      (->> (mapv #(get-image-url-from-media-item res-key %)))))

