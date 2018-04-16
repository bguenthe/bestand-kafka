package de.bguenthe.subscribewarenbewegungenmongo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class Warenbewegungslistener {

    @Autowired
    lateinit var warenbewegungenRepository: WarenbewegungenRepository

    @Autowired
    lateinit var countController: CountController

    @KafkaListener(topics = arrayOf("warenbewegungen"))
    fun listen(consumerRecord: ConsumerRecord<String?, String?>, acknowledgment: Acknowledgment) {
        val mapper = jacksonObjectMapper()

        val warenbewegungen: Warenbewegungen = mapper.readValue<Warenbewegungen>(consumerRecord.value().toString())
        warenbewegungenRepository.save(warenbewegungen)
        countController.correlationId = warenbewegungen.correlationid

        countController.counter.getAndIncrement()

        acknowledgment.acknowledge()
    }
}