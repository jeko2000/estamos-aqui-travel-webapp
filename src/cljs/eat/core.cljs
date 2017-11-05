(ns eat.core
  (:require [reagent.core :as reagent :refer [atom]]))

(defn sample []
  [:h3 "Sample"])

(reagent/render
 [sample]
 (.getElementById js/document "quick-links"))
