(ns apple-receipt.response
  (:require [apple-receipt.app-receipt :as app-receipt]
            [apple-receipt.iap-receipt :as iap-receipt]))

;; https://developer.apple.com/library/ios/releasenotes/General/ValidateAppStoreReceipt/Chapters/ValidateRemotely.html#//apple_ref/doc/uid/TP40010573-CH104-SW4
(defrecord Response
    [status
     environment ; undocumented
     receipt

     ;; These two latest_receipt fields are only returned for iOS 6 style
     ;; transaction receipts for auto-renewable subscriptions.
     ;; They may be returned for other receipts,
     ;; but they are considered undocumented if so, and many
     ;; users on the dev forums have complained that they're missing
     ;; See: https://forums.developer.apple.com/thread/51174
     ;; Note that this appears to be out of date: https://developer.apple.com/library/ios/technotes/tn2413/_index.html#//apple_ref/doc/uid/DTS40016228-CH1-RECEIPT-HOW_DO_I_USE_THE_CANCELLATION_DATE_FIELD_
     latest_receipt
     latest_receipt_info
     ])

(def org-map->Response map->Response)

(defn map->Response
  [map]
  (-> (org-map->Response map)
      (assoc :receipt (app-receipt/map->AppReceipt (:receipt map)))
      (assoc :latest_receipt_info (mapv iap-receipt/map->IAPReceipt (:latest_receipt_info map)))
      (assoc-in [:receipt :in_app] (mapv iap-receipt/map->IAPReceipt (get-in map [:receipt :in_app])))))
