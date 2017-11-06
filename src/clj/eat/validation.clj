(ns eat.validation
  (:require [bouncer.core :as b]
            [bouncer.validators :as v :refer [defvalidator]]))

(defvalidator image-resource
  "Validates value is a correct image-resource

  It implements a simple check to verify if the value is a string
  and it starts with /img/"
  {:default-message-format "%s must begin with /img/"}
  [value]
  (and (v/required value)
       (v/matches value #"^/img/")))

(defn validate-post [params]
  (first
   (b/validate
    params
    :title [v/required [v/min-count 5]]
    :author v/required
    :date v/required
    :md [v/required [v/min-count 10]]
    :preview v/required
    :preview_img image-resource
    :title_img image-resource
    :tags v/required)))
