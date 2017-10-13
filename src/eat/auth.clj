(ns eat.auth
  (:require [eat.db.core :refer [get-user get-user-from-creds]]
            [ring.util.response :refer [redirect]]))

(defn handle-login [{{username "username"
                    password "password" :as creds} :form-params
                   session :session}]
  (if-let [user (get-user-from-creds username password)]
    (assoc (redirect "/admin")
           :session (assoc session :identity (:uuid user)))
    (assoc (redirect "/login")
           :flash (assoc creds :error "Wrong Credentials"))))

(defn handle-logout [{:keys [session] :as req}]
  (assoc (redirect "/")
         :session nil))

(defn autheticated? [{:keys [session]}]
  (boolean (:identity session)))
