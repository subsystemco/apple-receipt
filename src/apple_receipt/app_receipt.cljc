(ns apple-receipt.app-receipt)

;; https://developer.apple.com/library/ios/releasenotes/General/ValidateAppStoreReceipt/Chapters/ReceiptFields.html#//apple_ref/doc/uid/TP40010573-CH106-SW2
(defrecord AppReceipt
    [receipt_type ; undocumented
     adam_id ; undocumented
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
     in_app])

;; https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/StoreKitGuide/Chapters/Products.html#//apple_ref/doc/uid/TP40008267-CH2-SW4
(defn type
  [receipt]
  (or :consumable-product
      :non-consumable-product
      :auto-renewable-subscription
      :non-renewable-subscription
      :free-subscription))

;; https://forums.developer.apple.com/thread/46737
(defn refunded?
  []
  false)
