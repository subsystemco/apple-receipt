(ns apple-receipt.core-test
  (:require [apple-receipt.core :refer [equal?]]
            [thermal.apple :as thermal]
            #?@(:clj  [[clojure.test :refer :all]
                       [clj-time.core :as t]]
                :cljs [[cljs.test :refer-macros [deftest is testing]]
                       [cljs-time.core :as t]])))

(deftest equal?-test
  (testing "should ignore request_dates and latest_receipt hash"
    (let [resp1 (thermal/response {:product "com.subsystem.subscription.monthly"
                                   :plan_duration (t/months 1)
                                   :start_date (t/date-time 2016 8 14 4 3 27 456)})
          resp2 (thermal/response {:product "com.subsystem.subscription.monthly"
                                   :plan_duration (t/months 1)
                                   :start_date (t/date-time 2016 8 14 4 3 27 456)})]
      (is (equal? resp1 resp2)))))
