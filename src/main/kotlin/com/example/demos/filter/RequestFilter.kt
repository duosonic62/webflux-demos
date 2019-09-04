package com.example.demos.filter

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class RequestFilter: WebFilter {
    companion object {
        private const  val TIMEOUT_DURATION = 5000L
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        chain.filter(exchange)
                // タイムアウトを設定
                .timeout(Duration.ofMillis(TIMEOUT_DURATION))

}