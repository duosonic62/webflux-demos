package com.example.demos.exceptionHandler

import org.springframework.boot.autoconfigure.web.ResourceProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono
import java.util.concurrent.TimeoutException

/**
 * OrderはデフォルトのExceptionHandlerは-1で設定されており、より早くExceptionをキャッチするため-2に設定。
 */
@Component
@Order(-2)
class ExceptionHandler(
        errorAttributes: ErrorAttributes,
        applicationContext: ApplicationContext,
        serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(errorAttributes, ResourceProperties(), applicationContext) {
    init {
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setMessageWriters(serverCodecConfigurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> =
            RouterFunctions.route(RequestPredicates.all(), HandlerFunction {
                when (val ex = getError(it)) {
                    is TimeoutException -> createServerResponse(HttpStatus.REQUEST_TIMEOUT)
                    else -> createServerResponse(HttpStatus.INTERNAL_SERVER_ERROR)
                }
            })

    private fun createServerResponse(status: HttpStatus): Mono<ServerResponse> =
            ServerResponse.status(status.value()).build()
}