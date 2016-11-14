(ns apple-receipt.core-test
  (:require [apple-receipt.record :as record]
            [thermal.apple :as thermal]
            #?@(:clj  [[clojure.test :refer :all]
                       [clj-time.core :as t]]
                :cljs [[cljs.test :refer-macros [deftest is testing]]
                       [cljs-time.core :as t]])))

(deftest equal?-test
  (testing "IAP uses core/= to validate equality"
    (let [x (thermal/iap {:product-id "com.subsystem.subscription.monthly"
                          :duration (t/months 1)
                          :purchase-date (t/date-time 2016 8 14 4 3 27 456)
                          :original-purchase-date (t/date-time 2016 8 14 4 3 27 456)})
          y (thermal/iap {:product-id "com.subsystem.subscription.monthly"
                          :duration (t/months 1)
                          :purchase-date (t/date-time 2016 8 14 4 3 27 456)
                          :original-purchase-date (t/date-time 2016 8 14 4 3 27 456)})]
      (is (record/equal? x y))))

  (testing "IAP uses core/= to validate inequality"
    (let [x (thermal/iap {:product-id "com.subsystem.subscription.monthly"
                          :duration (t/months 1)
                          :purchase-date (t/date-time 2016 8 14 4 3 27 456)
                          :original-purchase-date (t/date-time 2016 8 14 4 3 27 456)})
          y (thermal/iap {:product-id "com.subsystem.subscription.monthly"
                          :duration (t/months 6)
                          :purchase-date (t/date-time 2016 8 14 4 3 27 456)
                          :original-purchase-date (t/date-time 2016 8 14 4 3 27 456)})]
      (is (not (record/equal? x y)))))

  (testing "Receipt ignores request_dates to validate equality"
    (let [x (thermal/receipt (t/date-time 2016 8 14 4 3 27 456))
          y (thermal/receipt (t/date-time 2016 8 14 4 3 27 456))]
      (is (record/equal? x y))))

  (testing "Receipt considers fields other than request_dates to validate inequality"
    (let [x (thermal/receipt (t/date-time 2014 8 14 4 3 27 456))
          y (thermal/receipt (t/date-time 2016 8 14 4 3 27 456))]
      (is (not (record/equal? x y)))))

  (testing "Response ignores latest_receipt to validate equality"
    (let [x (thermal/response {:product "com.subsystem.subscription.monthly"
                               :plan_duration (t/months 1)
                               :start_date (t/date-time 2016 8 14 4 3 27 456)})
          y (thermal/response {:product "com.subsystem.subscription.monthly"
                               :plan_duration (t/months 1)
                               :start_date (t/date-time 2016 8 14 4 3 27 456)})]
      (is (record/equal? x y))))

  (testing "Response considers fields other than latest_receipt to validate inequality"
    (let [x (thermal/response {:product "com.subsystem.subscription.monthly"
                               :plan_duration (t/months 1)
                               :start_date (t/date-time 2016 8 14 4 3 27 456)})
          y (thermal/response {:product "com.subsystem.subscription.yearly"
                               :plan_duration (t/years 1)
                               :start_date (t/date-time 2016 8 14 4 3 27 456)})]
      (is (not (record/equal? x y))))))
