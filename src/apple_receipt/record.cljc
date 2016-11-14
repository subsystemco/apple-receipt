(ns apple-receipt.record)

(defprotocol AppleDataProtocol
  (equal? [x y]))

;; https://developer.apple.com/library/ios/releasenotes/General/ValidateAppStoreReceipt/Chapters/ReceiptFields.html#//apple_ref/doc/uid/TP40010573-CH106-SW12
(defrecord IAPReceipt
    [quantity
     product_id
     transcation_id
     original_transaction_id
     purchase_date
     purchase_date_ms ; undocumented
     purchase_date_pst ; undocumented
     original_purchase_date
     original_purchase_date_ms ; undocumented
     original_purchase_date_pst ; undocumented
     expires_date
     expires_date_ms ; undocumented
     expires_date_pst ; undocumented
     cancellation_date
     cancellation_date_ms ; undocumented
     cancellation_date_pst ; undocumented
     app_item_id
     version_external_identifier
     web_order_line_item_id
     is_trial_period] ; undocumented
  AppleDataProtocol
  (equal?
    [iap1 iap2]
    (= iap1 iap2)))

;; https://developer.apple.com/library/ios/releasenotes/General/ValidateAppStoreReceipt/Chapters/ReceiptFields.html#//apple_ref/doc/uid/TP40010573-CH106-SW2
(defrecord AppReceipt
    [receipt_type ; undocumented

     ; undocumented
     ; https://affiliate.itunes.apple.com/resources/blog/song-previews/
     ; http://stackoverflow.com/questions/13133786/is-there-any-globally-unique-indentifier-for-a-song-purchased-from-itunes#comment36473920_13133786
     adam_id

     app_item_id ; undocumented
     bundle_id
     application_version
     download_id ; undocumented
     version_external_identifier ; undocumented
     creation_date
     expiration_date
     receipt_creation_date ; undocumented, same as creation_date?
     receipt_creation_date_ms ; undocumented
     receipt_creation_date_pst ; undocumented
     request_date ; undocumented
     request_date_ms  ; undocumented
     request_date_pst ; undocumented
     original_purchase_date ; undocumented except on InApppurchasereceipt
     original_purchase_date_ms ; undocumented
     original_purchase_date_pst ; undocumented
     original_application_version
     in_app]
  AppleDataProtocol
  (equal?
    [rcpt1 rcpt2]
    (let [keys [:request_date :request_date_ms :request_date_pst]]
      (= (apply dissoc rcpt1 keys)
         (apply dissoc rcpt2 keys)))))

;; https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/StoreKitGuide/Chapters/Products.html#//apple_ref/doc/uid/TP40008267-CH2-SW4
(defn receipt-type
  [receipt]
  (or :consumable-product
      :non-consumable-product
      :auto-renewable-subscription
      :non-renewable-subscription
      :free-subscription))

;; https://forums.developer.apple.com/thread/46737
(defn receipt-refunded?
  []
  false)

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
     latest_receipt_info]
  AppleDataProtocol
  (equal?
    [resp1 resp2]
    (and
     (= (dissoc resp1 :receipt :latest_receipt)
        (dissoc resp2 :receipt :latest_receipt))
     (equal? (:receipt resp1)
             (:receipt resp2)))))

(defn api-json->Response
  [map]
  (-> (map->Response map)
      (assoc :receipt (map->AppReceipt (:receipt map)))
      (assoc :latest_receipt_info (mapv map->IAPReceipt (:latest_receipt_info map)))
      (assoc-in [:receipt :in_app] (mapv map->IAPReceipt (get-in map [:receipt :in_app])))))
