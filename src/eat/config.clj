(ns eat.config
  (:require [eat.util :refer [get-resource string->keyword]]))

(defn- env->map [env]
  (if-let [env-val (System/getenv env)]
    {(string->keyword env) env-val}))

(defn read-external-config-file [filepath]
  (if filepath
    (let [file (java.io.File. filepath)]
      (if (.exists file)
        (-> file slurp read-string)))))

(def config
  (delay
   (merge (-> "default_config.edn" get-resource slurp read-string)
          (-> "CONFIG" (System/getenv) read-external-config-file)
          (env->map "DATABASE_URL")
          (env->map "INSTAGRAM_ACCESS_TOKEN"))))
