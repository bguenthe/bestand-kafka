package de.bguenthe.subscribewarenbewegungenmongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "warenbewegungen")
data class Warenbewegungen(var process: String, @Id var uuid: String, val itemoption_commkey: String, val quantity: String,
                           val id_warehouse_credit: String, val id_warehouse_debit: String, val timestamp: String,
                           val compsupplierno: String, val customercompany: String, val promotion: String,
                           val salesprice: String, val id_stockpostingsource: String, val id_stockpostingtype: String,
                           val vouchernumer: String)