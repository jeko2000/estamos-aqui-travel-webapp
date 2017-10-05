(ns eat.web.util
  (:require [clojure.java.io :as io]))

;;Helpers
(defn string->uri [s]
  (java.net.URI. s))

(defn link-to
  ([url content]
   (link-to url nil content))
  ([url attr-map content]
   [:a (assoc attr-map :href (string->uri url)) content]))
