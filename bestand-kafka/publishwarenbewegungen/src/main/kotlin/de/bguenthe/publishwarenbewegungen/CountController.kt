package de.bguenthe.publishwarenbewegungen

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.InputStream
import java.util.*
import java.util.concurrent.atomic.AtomicLong

data class CountWarenbewegungen(val id: Long)

data class Warenbewegungen(var process: String, var uuid: String, val itemoption_commkey: String, val quantity: String,
                           val id_warehouse_credit: String, val id_warehouse_debit: String, val timestamp: String,
                           val compsupplierno: String, val customercompany: String, val promotion: String,
                           val salesprice: String, val id_stockpostingsource: String, val id_stockpostingtype: String,
                           val vouchernumer: String)

@RestController
class CountController {

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Value("classpath:Warenbewegungen_Intrastat_20180323_090001.csv")
    lateinit var res: Resource

    val counter = AtomicLong()

    @GetMapping("/count")
    fun count() =
            CountWarenbewegungen(counter.get())

    @GetMapping("/start")
    fun start() {
        val mapper = jacksonObjectMapper()

        var ins: InputStream = res.inputStream
        ins.bufferedReader().useLines { lines ->
            lines.forEach {
                var uuid: UUID = UUID.randomUUID()
                var splitted = it.split(";")
                val warenbewegungen = Warenbewegungen("publishWarenbewegungen", uuid.toString(), splitted[1], splitted[2], splitted[3], splitted[4], splitted[5], splitted[6], splitted[7], splitted[8], splitted[9], splitted[10], splitted[11], splitted[12])
                var jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(warenbewegungen)
                kafkaTemplate.send("warenbewegungen", uuid.toString(), jsonStr)
                counter.getAndIncrement()
            }
        }
    }

    @GetMapping("/slow")
    fun slow() {
        val mapper = jacksonObjectMapper()

        var ins: InputStream = res.inputStream
        ins.bufferedReader().useLines { lines ->
            lines.forEach {
                var uuid: UUID = UUID.randomUUID()
                var splitted = it.split(";")
                val warenbewegungen = Warenbewegungen("publishWarenbewegungen", uuid.toString(), splitted[1], splitted[2], splitted[3], splitted[4], splitted[5], splitted[6], splitted[7], splitted[8], splitted[9], splitted[10], splitted[11], splitted[12])
                var jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(warenbewegungen)
                kafkaTemplate.send("warenbewegungen", uuid.toString(), jsonStr)
                counter.getAndIncrement()
                if (counter.get() % 1000 == 0L) {
                    return
                }
            }
        }
    }
}