(ns apple-receipt.iap-receipt)

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
     is_trial_period ; undocumented
     ])
