(ns eat.test.test-helper
  (:require [eat.db.core :refer [*db*]]
            [eat.config :refer [config]]))

(def test-config
  {:server {:port 0}
   :layout {:tags-output-prefix "/tags"
            :static-resource-prefix "/static"
            :sidebar-latest-post-count 1}
   :static-resource-prefix "/static"})

(defn using-test-config [f]
  (let [original-config @config]
    (with-redefs [config (delay (merge (original-config test-config)))]
      (f))))

(defn default-fixture [f]
  (using-test-config
    f))
