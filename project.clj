(defproject apple-receipt "0.1.0-SNAPSHOT"
  :description "Utilities for working with receipts returned by Apple's StoreKit APIs and verifyReceipt endpoints."
  :url "https://github.com/leppert/apple-receipt"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript  "1.9.293"]
                 [com.cemerick/piggieback    "0.2.1"]]
  :plugins [[s3-wagon-private "1.2.0"]]

  :repositories {"snapshots" {:url "s3p://libs.subsystem.co/snapshots"
                              :username :env/aws_libs_access_key
                              :passphrase :env/aws_libs_secret_key}
                 "releases" {:url "s3p://libs.subsystem.co/releases"
                             :username :env/aws_libs_access_key
                             :passphrase :env/aws_libs_secret_key}}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]})
