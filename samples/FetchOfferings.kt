package payblast.sample

import payblast.HttpTransport
import payblast.PlayBilling
import payblast.Purchases

fun main() {
    val purchases = Purchases(
        baseUrl = "http://127.0.0.1:4000",
        http = PrintingHttp(),
        billing = object : PlayBilling {
            override fun purchase(productId: String) = productId
        },
    )
    purchases.configure("pk_replace_me", "sample-user")
    println(purchases.getOfferings())
}

private class PrintingHttp : HttpTransport {
    override fun get(path: String, apiKey: String): String = "GET $path"
    override fun post(path: String, apiKey: String, body: String): String = body
}
