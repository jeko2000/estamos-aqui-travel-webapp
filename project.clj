(defproject eat "1.0.4"
  :description "The web application for http://estamosaquitravel.com"
  :url "https://estamosaquitravel.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [me.raynes/cegdown "0.1.1"]
                 [bouncer "1.0.1"]
                 [buddy "2.0.0"]
                 [org.clojure/java.jdbc "0.7.3"]
                 [org.postgresql/postgresql "42.1.4"]
                 [clj-http "3.7.0"]
                 [cheshire "5.8.0"]
                 [clucy "0.4.0"]
                 [reagent "0.7.0"]
                 [cljs-ajax "0.7.3"]
                 [ring-middleware-format "0.7.2"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/tools.reader "1.1.0"]
                 [enlive "1.1.6"]
                 [clj-pdf "2.2.30"]]
  :source-paths ["src/clj"]
  :cljsbuild
  {:builds [{:id "dev"
             :source-paths ["src/cljs"]
             :figwheel true
             :compiler {:main eat.core
                        :asset-path "/static/js/out"
                        :output-to "/var/www/static/js/app.js"
                        :output-dir "/var/www/static/js/out"
                        :optimizations :none
                        :source-map true
                        :source-map-timestamp true
                        :pretty-print true}}
            {:id "min"
             :source-paths ["src/cljs"]
             :compiler {:main eat.core
                        :output-to "/var/www/static/js/app.js"
                        :optimizations :advanced}}]}
  :profiles {:dev {:main eat.dev
                   :dependencies [[org.clojure/clojurescript "1.9.946"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [figwheel-sidecar "0.5.14"]]
                   :plugins [[lein-figwheel "0.5.14"]
                             [lein-cljsbuild "1.1.1"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :clean-targets ^{:protect false} ["resources/public/js"
                                                     :target-path]}
             :uberjar {:aot :all}}
  :main eat.core)
