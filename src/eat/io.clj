(ns eat.io
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

;;TODO: Add this to config
(def posts-prefix "post")
(def default-author "Shell")
(def number-of-most-recent-posts 5)

;;TODO Figure out tag management
;;TODO Handle preview images. Compute blurb from article

(defn get-resource [resource]
  (slurp (io/resource resource)))

(defn load-edn [name]
  (-> name (io/resource) slurp read-string))

(defn- get-post-names [root]
  (let [folder (-> root io/resource io/file)]
    (map #(.getName %) (rest (file-seq folder)))))

(defn- parse-raw-title [^String name]
  (.substring name 11))

(defn- parse-date [^String name]
  (.substring name 0 10))

(defn- parse-title [raw-title]
  (str/replace
   (str/replace raw-title "-" " ")
   #"\..*$" ""))

(defn- parse-url [raw-title]
  (str "/" posts-prefix "/" (str/replace raw-title #"\..*$" "")))

(defn- prepare-edn-item [root name]
  (let [raw-title (parse-raw-title name)
        post-date (parse-date name)]
    {:title (parse-title raw-title)
     :date post-date
     :file (str root "/" name)
     :url (parse-url raw-title)}))

(defn build-edn! [root target]
  (let [file-names (get-post-names root)
        edn (mapv (partial prepare-edn-item root) file-names)]
    (spit target edn)))

