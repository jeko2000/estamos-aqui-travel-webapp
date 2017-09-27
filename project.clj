(defproject eat "0.1.1"
  :description "This is the web application that builds our our blog, Estamos Aqui Travel"
  :url "http://estamosaquitravel.com"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [stasis "2.3.0"]
                 [ring "1.6.2"]
                 [hiccup "1.0.5"]
                 [prone "1.1.4"]
                 [me.raynes/cegdown "0.1.1"]
                 [optimus "0.20.1"]
                 [midje "1.8.3"]]
  :plugins [[lein-ring "0.12.1"]]
  :aliases {"build-site" ["run" "-m" "eat.web/export"]}
  :main eat.system
  :ring {:handler eat.web/app}
  :profiles {:dev {:plugins [[lein-ring "0.8.10"]]}
             :test {:dependencies [[midje "1.8.3"]]                    
                    :plugins [[lein-midje "3.1.3"]]}})
