package de.bguenthe.subscribewarenbewegungenstreamprocessor

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import kotlin.collections.HashMap


@Component()
class Streamprocessor {

    data class Bestand(var typ: String, var quantity: Long)

    val bestand = HashMap<String, Long>()

    private val inTopic = "warenbewegungen"
    private val outTopic = "bestandsbewegungen"
    private var streams: KafkaStreams? = null

    @Autowired
    lateinit var countController: CountController

    @PostConstruct
    fun runStream() {
        val stringSerde = Serdes.String()

        val config = Properties()
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "bestandsbewegungen")
        config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, stringSerde.javaClass.name)
        config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, stringSerde.javaClass.name)
        config.put(StreamsConfig.CLIENT_ID_CONFIG, "streamprocessot") // damit beide prozesse in diesem springbootprocess streams lesen
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092")
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

        val builder = KStreamBuilder()
        val kstream: KStream<String, String> = builder.stream(inTopic)

//        kstream.foreach { _, value ->
//            val mapper = jacksonObjectMapper()
//            val warenbewegungen: Warenbewegungen = mapper.readValue<Warenbewegungen>(value)
//            val list: List<String> = listOf("300", "310", "330", "340", "336", "346")
//            print("${warenbewegungen.id_stockpostingtype}, ${warenbewegungen.id_stockpostingtype.length}")
//            print(warenbewegungen.id_stockpostingtype.length == 4 && warenbewegungen.id_stockpostingtype.substring(0..2) in list)
//        }

        kstream.filter { _, value ->
            val mapper = jacksonObjectMapper()
            val warenbewegungen: Warenbewegungen = mapper.readValue<Warenbewegungen>(value)
            val list: List<String> = listOf("300", "310", "330", "340", "336", "346")
            warenbewegungen.id_stockpostingtype.length == 4 && warenbewegungen.id_stockpostingtype.substring(0..2) in list
        }.map { key, value ->
            var typ: String = ""
            var quantity: Long = 0
            val mapper = jacksonObjectMapper()
            val warenbewegungen: Warenbewegungen = mapper.readValue<Warenbewegungen>(value)
            if (warenbewegungen.id_stockpostingtype.substring(0..2) == "300") {
                typ = "BUB"
                quantity = warenbewegungen.quantity.toLong()
            } else if (warenbewegungen.id_stockpostingtype.substring(0..2) == "310") {
                typ = "BUB"
                quantity = warenbewegungen.quantity.toLong() * -1
            } else if (warenbewegungen.id_stockpostingtype.substring(0..2) == "330") {
                typ = "LIB"
                quantity = warenbewegungen.quantity.toLong()
            } else if (warenbewegungen.id_stockpostingtype.substring(0..2) == "340") {
                typ = "LIB"
                quantity = warenbewegungen.quantity.toLong() * -1
            } else if (warenbewegungen.id_stockpostingtype.substring(0..2) == "336") {
                typ = "TLIB"
                quantity = warenbewegungen.quantity.toLong()
            } else if (warenbewegungen.id_stockpostingtype.substring(0..2) == "346") {
                typ = "TLIB"
                quantity = warenbewegungen.quantity.toLong() * -1
            }

            countController.processed.getAndIncrement()
            KeyValue(typ, """{"typ":"${typ}", "quantity":"${quantity}", "correlationid":"${warenbewegungen.correlationid}", "sourceprocess":"${warenbewegungen.process}", "currentprocess":"streamprocessor"}}}""")
        }

        val bestand: Bestand = Bestand("", 0L)
        val agg: KTable<Windowed<String>, Long>? = kstream.groupByKey().windowedBy(TimeWindows.of(TimeUnit.SECONDS.toMillis(1)))
                .aggregate({ bestand.quantity }, { key, value, value1 -> value1.toLong() + value.toLong()})
        val nagg = agg!!.toStream()
        nagg.to(outTopic)
        streams = KafkaStreams(builder, config)
        streams!!.start()
    }

    @PreDestroy
    fun closeStream() {
        streams!!.close()
    }
}