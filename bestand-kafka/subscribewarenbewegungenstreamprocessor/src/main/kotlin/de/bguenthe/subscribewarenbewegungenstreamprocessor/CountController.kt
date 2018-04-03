package de.bguenthe.subscribewarenbewegungenstreamprocessor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

data class CountWarenbewegungen(val id: Long)

@RestController
class CountController {

    val counter = AtomicLong()

    @GetMapping("/count")
    fun count() =
            CountWarenbewegungen(counter.get())
}