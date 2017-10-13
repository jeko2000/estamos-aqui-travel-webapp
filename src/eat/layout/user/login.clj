(ns eat.layout.user.login
  (:require [eat.layout.user.base :refer [base]]
            [hiccup.form :as f]))

(defn login-form []
  (f/form-to {:id "login" :class "well"} ["POST" "/login"]
             [:div {:class "form-group"}
              (f/label "username" "User Name")
              (f/text-field {:class "form-control" :placeholder "Enter username"} "username")]
             [:div {:class "form-group"}
              (f/label "password" "Password")
              (f/password-field {:class "form-control" :placeholder "Password"} "password")]
             (f/submit-button {:class "btn btn-primary btn-block"} "Login")))

(defn login-main [error]
  [:div {:class "container"}
   [:div {:class "row"}
    [:div {:class "col-md-4 col-md-offset-4"}
     [:h2 {:class "text-center"} "Please sign in"]
     (login-form)
     (if error
       [:p {:class "text-danger"} error])]]])

(defn login-page [layout-config {:keys [error]}]
  (base layout-config
        {:title "Login"
         :content (login-main error)}))
