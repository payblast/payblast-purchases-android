package payblast

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PurchasesTest {
    @Test
    fun purchaseUsesPlayBillingAndPaywallRendersLookupKeys() {
        val http = FakeHttp()
        val billing = FakeBilling()
        val purchases = Purchases("http://127.0.0.1:4000", http, billing)
        purchases.configure("pk_test", "user-1")
        assertTrue(purchases.getOfferings().contains("monthly"))
        val info = purchases.purchase("pro_monthly")
        assertEquals("pro_monthly", billing.purchased)
        assertTrue(info.contains("is_active"))
        assertEquals("pk_test", http.apiKey)
        val rendered = purchases.presentPaywall(
            """{"blocks":[{"type":"package_picker"}]}""",
            listOf("monthly", "annual"),
        )
        assertTrue(rendered.contains("monthly"))
        assertTrue(rendered.contains("annual"))
    }
}

private class FakeHttp : HttpTransport {
    var apiKey: String = ""

    override fun get(path: String, apiKey: String): String {
        this.apiKey = apiKey
        return if (path.endsWith("/offerings")) {
            """{"offerings":[{"packages":[{"lookup_key":"monthly"}]}]}"""
        } else {
            """{"app_user_id":"user-1","entitlements":{"pro":{"is_active":true}}}"""
        }
    }

    override fun post(path: String, apiKey: String, body: String): String = get(path, apiKey)
}

private class FakeBilling : PlayBilling {
    var purchased: String = ""

    override fun purchase(productId: String): String {
        purchased = productId
        return "token-1"
    }
}
