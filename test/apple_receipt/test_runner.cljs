(ns apple-receipt.test-runner
 (:require [doo.runner :refer-macros [doo-tests]]
           [apple-receipt.core-test]
           [cljs.nodejs :as nodejs]))

(try
  (.install (nodejs/require "source-map-support"))
  (catch :default _))

(doo-tests
 'apple-receipt.core-test)
