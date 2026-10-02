# payblast-purchases-android

Payblast Android SDK (Play Billing)

This repository does not vendor RevenueCat source. The public API shape is studied from [https://github.com/RevenueCat/purchases-android](https://github.com/RevenueCat/purchases-android) and reimplemented against Payblast.

## Contract

```text
configure(apiKey, appUserId)
logIn(appUserId) / logOut()
getOfferings()
purchase(package)
restore()
getCustomerInfo()
presentPaywall(offering?)
```

```bash
./gradlew test
```

`samples/FetchOfferings.kt` calls `getOfferings` against `http://127.0.0.1:4000`. Play Billing stays behind `PlayBilling` so unit tests do not need a device.

`getCustomerInfo` exposes `entitlements[lookupKey].isActive`. Packages carry the store product identifier for this SDK's platform. Purchases of digital goods inside the native app go through that store. Web purchases use Stripe Checkout on the app maker's connected account.

## Reference

- https://github.com/RevenueCat/purchases-android
