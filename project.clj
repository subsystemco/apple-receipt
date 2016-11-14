(defproject apple-receipt "0.1.0-SNAPSHOT"
  :description "Utilities for working with receipts returned by Apple's StoreKit APIs and verifyReceipt endpoints."
  :url "https://github.com/leppert/apple-receipt"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript  "1.9.293"]
                 [com.cemerick/piggieback    "0.2.1"]
                 [clj-time "0.12.0"]
                 [com.andrewmcveigh/cljs-time "0.5.0-alpha2"]
                 [thermal "0.1.0-SNAPSHOT"]
                 [medley "0.8.4"]]
  :plugins [[s3-wagon-private "1.2.0"]
            [lein-cljsbuild "1.1.4"]
            [lein-npm       "0.6.2"]
            [lein-doo       "0.1.7"]]

  :npm {:dependencies [[source-map-support "0.4.6"]]}

  :repositories {"snapshots" {:url "s3p://libs.subsystem.co/snapshots"
                              :username :env/aws_libs_access_key
                              :passphrase :env/aws_libs_secret_key}
                 "releases" {:url "s3p://libs.subsystem.co/releases"
                             :username :env/aws_libs_access_key
                             :passphrase :env/aws_libs_secret_key}}

  :doo {:build "test"
        :alias {:default [:node]}}

  :cljsbuild
  {:builds {:production {:source-paths ["src"]
                         :compiler {:output-to     "target/apple-receipt/apple_receipt.js"
                                    :output-dir    "target/apple-receipt"
                                    :source-map    "target/apple-receipt/apple_receipt.js.map"
                                    :target        :nodejs
                                    :language-in   :ecmascript5
                                    :optimizations :advanced
                                    }}
            :test {:source-paths ["src" "test"]
                   :compiler {:output-to     "target/apple-receipt-test/apple_receipt.js"
                              :output-dir    "target/apple-receipt-test"
                              :target        :nodejs
                              :language-in   :ecmascript5
                              :optimizations :none
                              :main          apple-receipt.test-runner}}}}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]})
