(ns eat.init
  (:require [eat.db.migrate :refer [migrate!-if-needed]]
            [eat.db.core :refer [*db*]]
            [eat.search :refer [flush-and-index-all-posts!]]))

(defn init []
  (migrate!-if-needed *db*)
  (flush-and-index-all-posts!))
