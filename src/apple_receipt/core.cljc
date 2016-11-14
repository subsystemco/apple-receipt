(ns apple-receipt.core
  (:require [medley.core :as m]))

(defn- remove-per-req-data
  [resp]
  (-> (dissoc resp :latest_receipt)
      (assoc :receipt
             (dissoc (:receipt resp)
                     :request_date
                     :request_date_ms
                     :request_date_pst))))

(defn equal?
  [r1 r2]
  (= (remove-per-req-data r1)
     (remove-per-req-data r2)))
