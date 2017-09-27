(ns eat.log)

(defn- build-log [type items]
  (println type (apply str items)))

(defn trace [& items] (build-log "TRACE" items))

(defn debug [& items] (build-log "DEBUG" items))

(defn info [& items] (build-log "INFO" items))

(defn warn [& items] (build-log "WARN" items))

(defn fatal [& items] (build-log "FATAL" items))

