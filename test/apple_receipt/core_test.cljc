(ns apple-receipt.core-test
  (:require #?@(:clj  [[clojure.test :refer :all]]
                :cljs [[cljs.test :refer-macros [deftest is testing]]])))

(deftest a-test
  (testing "Passing test"
    (is (= 1 1))))
