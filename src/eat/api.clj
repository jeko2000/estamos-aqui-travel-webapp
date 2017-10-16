(ns eat.api
  (:require [clj-http.client :as client]))

(def ^:dynamic *access-token*
  (-> "profiles.clj"
      slurp
      read-string
      :profiles/dev
      :env
      :access-token))

(defn get-recent-media-vector [token count]
  (-> "https://api.instagram.com/v1/users/self/media/recent/?access_token=%s&count=%s"
      (format token count)
      (client/get {:as :json})
      (get-in [:body :data])))

(defn get-image-url-from-media-item [res-key {:keys [link images]}]
  "The value for RES-KEY must be one of :thumbnail, :low_resolution, or :standard_resolution"
  {:link link
   :image (get-in images [res-key :url])})

(defn get-recent-images [count res-key]
  (-> *access-token*
      (get-recent-media-vector count)
      (->> (mapv #(get-image-url-from-media-item res-key %)))))
