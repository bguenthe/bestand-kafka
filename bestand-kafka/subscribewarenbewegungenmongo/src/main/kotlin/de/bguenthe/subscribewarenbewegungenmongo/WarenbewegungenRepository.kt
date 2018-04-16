package de.bguenthe.subscribewarenbewegungenmongo

import org.springframework.data.repository.CrudRepository
import java.util.*

interface WarenbewegungenRepository : CrudRepository<Warenbewegungen, String> {
    override fun findById(id: String): Optional<Warenbewegungen>

    fun findTopByOrderByProcessDesc(): Warenbewegungen

    fun findBycorrelationid(correlationid: String): Warenbewegungen
}