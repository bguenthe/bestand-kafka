package de.bguenthe.subscribebestandsbewegungen

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import java.time.Duration
import java.util.function.Consumer

@Configuration
class WebSocketConfiguration {

    @Autowired
    lateinit var countController: CountController

    @Bean
    fun wsha() = WebSocketHandlerAdapter()

    @Bean
    fun hm(): HandlerMapping {
        val suhm = SimpleUrlHandlerMapping()
        suhm.order = 0
        suhm.urlMap = mapOf("/subscribebestandsbewegungen/count" to wsh())
        return suhm
    }

    fun wsh(): WebSocketHandler {
        return WebSocketHandler { session ->
            val om = ObjectMapper()
            val publisher = Flux.generate(Consumer<SynchronousSink<String>> { sink ->
                sink.next(countController.bublibtlib)
            }).map { om.writeValueAsString(it) }
                    .map { session.textMessage(it) }
                    .delayElements(Duration.ofSeconds(1L))
            session.send(publisher)
        }
    }
}

