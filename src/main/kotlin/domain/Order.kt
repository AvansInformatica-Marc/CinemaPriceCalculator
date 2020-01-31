package domain

import kotlinx.coroutines.*
import java.io.BufferedWriter
import java.io.FileWriter
import java.time.DayOfWeek

class Order(val orderNr: Int, private val isStudentOrder: Boolean) {
    private val tickets = mutableListOf<MovieTicket>()

    private val premiumTicketFee = if(isStudentOrder) 2.0 else 3.0
    private val hasGroupDiscount
        get() = !isStudentOrder && tickets.size >= 6

    fun addSeatReservation(ticket: MovieTicket) {
        tickets += ticket
    }

    fun isSecondTicketFree(dayOfWeek: DayOfWeek) =
        isStudentOrder || dayOfWeek in daysWhereSecondTicketIsAlwaysFree

    fun getEffectivePricesPerTicket(): Map<MovieTicket, Double> {
        val map = mutableMapOf<MovieTicket, Double>()

        for(ticket in tickets) {
            var price = ticket.price

            if(ticket.isPremiumTicket)
                price += premiumTicketFee

            if(hasGroupDiscount)
                price -= price * 0.1

            if(tickets.indexOf(ticket) % 2 == 1 && isSecondTicketFree(ticket.date.dayOfWeek))
                price = 0.0

            map[ticket] = price
        }

        return map
    }

    fun calculatePrice(): Double {
        var totalPrice = 0.0

        for((_, effectivePrice) in getEffectivePricesPerTicket())
            totalPrice += effectivePrice

        return totalPrice
    }

    suspend fun export(exportFormat: TicketExportFormat) {
        val fileName = "Order_$orderNr.${exportFormat.fileExtension}"
        val isJson = exportFormat == TicketExportFormat.JSON
        val separator = if(isJson) ",\n" else "\n"
        val prefix = if(isJson) "[\n" else ""
        val postfix = if(isJson) "\n]" else ""
        val fileContent = tickets.joinToString(separator, prefix, postfix) {
            if(isJson) "\"$it\"" else "$it"
        }
        coroutineScope {
            launch(Dispatchers.IO) {
                FileWriter(fileName).use { fileWriter ->
                    BufferedWriter(fileWriter).use {
                        it.write(fileContent)
                    }
                }
            }
        }
    }

    companion object {
        private val daysWhereSecondTicketIsAlwaysFree =
            arrayOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY)
    }
}