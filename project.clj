(defproject eat "0.2.13"
  :description "The web application for http://estamosaquitravel.com"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.2"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [me.raynes/cegdown "0.1.1"]
                 [buddy "2.0.0"]
                 [org.clojure/java.jdbc "0.7.3"]                 
                 [org.postgresql/postgresql "42.1.4"]]
  :main eat.core)
