(ns eat.validation
  (:require [bouncer.core :as b]
            [bouncer.validators :as v :refer [defvalidator]]))

(defvalidator image-resource
  {:default-message-format "%s must begin with /img/"}
  [value]
  (and (v/required value)
       (v/matches value #"^/img/")))

(defn validate-post [params]
  (first
   (b/validate
    params
    :title [v/required [v/min-count 5 :message "title must have at least 5 characters"]]
    :author v/required
    :date [v/required [v/matches #"\d{4}-\d{2}-\d{2}" :message "date must have the form yyyy-mm-dd"]]
    :md [v/required [v/min-count 10 :message "title must have at least 10 characters"]]
    :preview v/required
    :preview_img image-resource
    :title_img image-resource
    :tags v/required)))
