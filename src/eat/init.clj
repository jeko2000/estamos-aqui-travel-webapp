(ns eat.init
  (:require [eat.db.migrate :refer [migrated? migrate!]]
            [eat.db.core :refer [*db*]]
            [eat.search :refer [flush-and-index-all-posts!]]))

(defn init []
  (or (migrated? *db*) (migrate! *db*))
  (flush-and-index-all-posts!))
