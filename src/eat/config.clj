(ns eat.config
  (:require [eat.util :refer [get-resource string->keyword]]))

(defn- env->map [env]
  (if-let [env-val (System/getenv env)]
    {(string->keyword env) env-val}))

(def default-config
  (delay
   (if-let [conf (get-resource "default_config.edn")]
     (-> conf slurp read-string)
     (println "WARNING: failed to find default_config.edn"))))

(def config
  (delay
   (merge @default-config
          (env->map "DATABASE_URL")
          (env->map "INSTAGRAM_ACCESS_TOKEN"))))
