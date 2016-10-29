(ns apple-receipt.status-code)

(def success
  "This receipt was properly validated."
  0)

(def json-unreadable
  "The App Store could not read the JSON object you provided."
  21000)

(def data-malformed
  "The data in the receipt-data property was malformed or missing."
  21002)

(def unable-to-auth
  "The receipt could not be authenticated."
  21003)

(def secret-mismatch
  "The shared secret you provided does not match the shared secret on file for your account.
  Only returned for iOS 6 style transaction receipts for auto-renewable subscriptions."
  21004)

(def server-unavailable
  "The receipt server is not currently available."
  21005)

(def valid-but-expired  
  "This receipt is valid but the subscription has expired. When this status code is returned to your server, the receipt data is also decoded and returned as part of the response.
  Only returned for iOS 6 style transaction receipts for auto-renewable subscriptions."
  21006)

(def from-test-env
  "This receipt is from the test environment, but it was sent to the production environment for verification. Send it to the test environment instead."
  21007)

(def from-prod-env
  "This receipt is from the production environment, but it was sent to the test environment for verification. Send it to the production environment instead."
  21008)
