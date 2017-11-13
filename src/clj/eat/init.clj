(ns eat.init
  (:require [eat.db.migrate :refer [migrate!]]
            [eat.db.core :refer [*db*]]
            [eat.search :refer [flush-and-index-all-posts!]]))

(defn init []
  (migrate! *db*)
  (flush-and-index-all-posts!))
