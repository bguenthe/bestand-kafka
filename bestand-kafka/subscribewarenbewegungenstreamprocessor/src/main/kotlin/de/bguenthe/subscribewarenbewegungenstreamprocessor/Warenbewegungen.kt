package de.bguenthe.subscribewarenbewegungenstreamprocessor

data class Warenbewegungen(var process: String, var correlationid: String, val itemoption_commkey: String, val quantity: String,
                           val id_warehouse_credit: String, val id_warehouse_debit: String, val timestamp: String,
                           val compsupplierno: String, val customercompany: String, val promotion: String,
                           val salesprice: String, val id_stockpostingsource: String, val id_stockpostingtype: String,
                           val vouchernumer: String)