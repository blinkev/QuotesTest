package com.example.quotes.data.net

import android.util.Log
import com.example.quotes.domain.Quote
import com.example.quotes.utils.JsonHelper
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import okhttp3.*
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface WebSocketHelper {
    fun subscribe(quotes: List<Quote>): Observable<List<Quote>>
    fun unsubscribe(quote: Quote)
}

class WebSocketHelperImpl @Inject constructor(
    private val jsonHelper: JsonHelper,
    private val mapper: SubscribedListDtoMapper
) : WebSocketListener(), WebSocketHelper {

    companion object {
        private const val LOG_TAG = "WebSocket"
        private const val NORMAL_CLOSURE_CODE = 1000
    }

    private var socket: WebSocket? = null
    private val client: OkHttpClient = createClient()

    private val webSocketPublisher: PublishSubject<List<Quote>> = PublishSubject.create()

    @Volatile
    private var quotes: List<Quote> = emptyList()

    private var reconnectionDisposable: Disposable? = null

    private fun createClient(): OkHttpClient {
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {

                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return emptyArray()
                }
            })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
            .build()
    }

    private fun startConnection() {
        Log.w(LOG_TAG, "startConnection")
        val request = Request.Builder().url("wss://quotes.eccalls.mobi:18400")
        socket = client.newWebSocket(request.build(), this)
    }

    override fun subscribe(quotes: List<Quote>): Observable<List<Quote>> {
        this.quotes = quotes
        startConnection()
        return webSocketPublisher.hide().doOnDispose {
            closeConnection()
        }
    }

    override fun unsubscribe(quote: Quote) {
        quotes = quotes.filterNot { it.name == quote.name }
        socket?.send("UNSUBSCRIBE: ${quote.name}")
        if (quotes.isEmpty()) closeConnection()
    }

    private fun closeConnection() {
        Log.w(LOG_TAG, "closeConnection")
        reconnectionDisposable?.dispose()
        socket?.close(NORMAL_CLOSURE_CODE, "Disconnected by client")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.w(LOG_TAG, "onOpen: ${response.body}")
        webSocket.send("SUBSCRIBE: ${quotes.joinToString(separator = ",") { it.name }}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.w(LOG_TAG, "onMessage: $text")
        val result: List<Quote> = parseTicks(text)
        if (result.isNotEmpty()) webSocketPublisher.onNext(result)
    }

    private fun parseTicks(message: String): List<Quote> {
        return tryParseQuotesSubscriptionResponse(message)
            ?: tryParseSubscribedListDto(message)
            ?: emptyList()
    }

    private fun tryParseQuotesSubscriptionResponse(message: String): List<Quote>? {
        return jsonHelper.fromJson(message, QuotesSubscriptionResponse::class.java)
            ?.subscribedList
            ?.let(mapper::map)
    }

    private fun tryParseSubscribedListDto(message: String): List<Quote>? {
        return jsonHelper.fromJson(message, SubscribedListDto::class.java)?.let(mapper::map)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.w(LOG_TAG, "onFailure -> ${t::class.java.name}, response=${response?.body}")
        t.printStackTrace()
        tryReConnection()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.w(LOG_TAG, "onClosed -> code: $code, reason: $reason")
    }

    private fun tryReConnection() {
        reconnectionDisposable = Observable.timer(5, TimeUnit.SECONDS)
            .subscribeBy(
                onNext = {
                    Log.w(LOG_TAG, "reconnection")
                    startConnection()
                }
            )
    }
}
