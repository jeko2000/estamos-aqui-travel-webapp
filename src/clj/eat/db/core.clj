(ns eat.db.core
  (:require [eat.config :refer [config]]))

(def ^:dynamic *db* (:database-url @config))
