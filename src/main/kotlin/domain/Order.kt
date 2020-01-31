package domain

import domain.TicketExportFormat.JSON
import domain.TicketExportFormat.PLAINTEXT

data class Order(val orderNr: Int) {
    val tickets = mutableListOf<MovieTicket>()

    fun addSeatReservation(ticket: MovieTicket) {
        tickets += ticket
    }

    fun calculatePrice(): Double {
        var totalPrice = 0.0

        for(ticket in tickets)
            totalPrice += calculatePriceForTicket(tickets.size, tickets.indexOf(ticket), ticket)

        return totalPrice
    }

    fun getExportedFileContent(exportFormat: TicketExportFormat) = when(exportFormat) {
        JSON -> tickets.joinToString(",\n", "[\n", "\n]") { "\"$it\"" }
        PLAINTEXT -> tickets.joinToString("\n")
    }

    suspend fun export(exportFormat: TicketExportFormat) =
        writeToFile("Order_$orderNr.${exportFormat.fileExtension}", getExportedFileContent(exportFormat))
}