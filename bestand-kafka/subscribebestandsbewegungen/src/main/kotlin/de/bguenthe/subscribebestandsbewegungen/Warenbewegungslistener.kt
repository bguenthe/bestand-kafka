package de.bguenthe.subscribebestandsbewegungen

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

data class Bestand(val typ: String, val quantity: Long)

@Component
class Warenbewegungslistener {
    @Autowired
    lateinit var countController: CountController

    var bub = 0L
    var lib = 0L
    var tlib = 0L

    @KafkaListener(topics = arrayOf("bestandsbewegungen"))
    fun listen(consumerRecord: ConsumerRecord<String?, String?>, acknowledgment: Acknowledgment) {
        val mapper = jacksonObjectMapper()

        val bestand: Bestand = mapper.readValue<Bestand>(consumerRecord.value()!!)

        if (bestand.typ == "BUB") {
            bub += bestand.quantity
        } else if (bestand.typ == "LIB") {
            lib += bestand.quantity
        } else if (bestand.typ == "TLIB") {
            tlib += bestand.quantity
        }

        println("BUB: ${bub}, LIB: ${lib}, TLIB: ${tlib}")
        countController.bublibtlib = "BUB: ${bub}, LIB: ${lib}, TLIB: ${tlib}"

        acknowledgment.acknowledge()
    }
}