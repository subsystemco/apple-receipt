(ns apple-receipt.calculations)

(defn plan
  [receipt]
  (:product_id (newest-iap receipt)))

(defn created-at
  []
  ;; https://developer.apple.com/library/ios/releasenotes/General/ValidateAppStoreReceipt/Chapters/ReceiptFields.html#//apple-ref/doc/uid/TP40010573-CH106-SW4
  ;; In an auto-renewable subscription receipt, this indicates the beginning of the subscription period, even if the subscription has been renewed.
  oldest-iap.original.purchased-at.try(:utc))

(defn start
  [receipt]
  (:original_purchase_date (newest-iap receipt)))

(defn ended-at
  []
  if canceled-at
  canceled-at
  elsif newest-iap.expires-at <= Time.zone.now
  newest-iap.expires-at.try(:utc))

(defn trial-start
  []
  return unless plan.trial?
  oldest-iap-of-product(newest-iap.product-id).purchased-at.try(:utc))

(defn trial-end
  []
  return unless plan.trial?
  oldest-iap-of-product(newest-iap.product-id).expires-at.try(:utc))

(defn canceled-at
  []
  ;; https://developer.apple.com/library/ios/releasenotes/General/ValidateAppStoreReceipt/Chapters/ReceiptFields.html#//apple-ref/doc/uid/TP40010573-CH106-SW19
  ;; For a transaction that was canceled by Apple customer support, the time and date of the cancellation.
  newest-iap.cancellation-at.try(:utc))

(defn current-period-end
  []
  ;; https://developer.apple.com/library/ios/releasenotes/General/ValidateAppStoreReceipt/Chapters/ReceiptFields.html#//apple-ref/doc/uid/TP40010573-CH106-SW28
  newest-iap.expires-at.try(:utc) if newest-iap.expires-at.present? && newest-iap.expires-at > Time.zone.now)

(defn current-period-start
  []
  ;; https://developer.apple.com/library/ios/releasenotes/General/ValidateAppStoreReceipt/Chapters/ReceiptFields.html#//apple-ref/doc/uid/TP40010573-CH106-SW15
  ;; In an auto-renewable subscription receipt, this is always the date when the subscription was purchased or renewed, regardless of whether the transaction has been restored.
  newest-iap.purchased-at.try(:utc) if current-period-end)

(defn iaps
  [receipt]
  (or (not-empty (:latest-receipt-info receipt))
      (:in-app receipt)))

(defn iaps-order-by-purchased-at
  []
  iaps.sort { |a, b| b.purchased-at <=> a.purchased-at })

(defn newest-iap
  []
  iaps-order-by-purchased-at.first)

(defn oldest-iap
  []
  iaps-order-by-purchased-at.last)

(defn oldest-iap-of-product(product-id)
  []
  iaps-order-by-purchased-at.select { |iap| iap.product-id == product-id }.last)
