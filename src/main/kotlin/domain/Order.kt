package domain

import domain.TicketExportFormat.JSON
import domain.TicketExportFormat.PLAINTEXT
import domain.pricing.CombinedPricingStrategy
import domain.pricing.OrderPricingStrategy
import domain.states.CreatedState
import domain.states.OrderState

data class Order(val orderNr: Int) {
    private val tickets = mutableListOf<MovieTicket>()

    private val pricingStrategy: OrderPricingStrategy = CombinedPricingStrategy()

    private var state: OrderState = CreatedState()

    fun addSeatReservation(ticket: MovieTicket) {
        tickets += ticket
    }

    private fun resetTicketPrices() {
        for(ticket in tickets) {
            ticket.calculatedPrice = ticket.initialPrice
        }
    }

    fun calculatePrice(): Double {
        resetTicketPrices()

        pricingStrategy.modifyTicketPrices(tickets)

        var totalPrice = 0.0

        for(ticket in tickets) {
            totalPrice += ticket.calculatedPrice
        }

        return totalPrice
    }

    fun getExportedFileContent(exportFormat: TicketExportFormat) = when(exportFormat) {
        JSON -> tickets.joinToString(",\n", "[\n", "\n]") { "\"$it\"" }
        PLAINTEXT -> tickets.joinToString("\n")
    }

    suspend fun export(exportFormat: TicketExportFormat) =
        writeToFile("Order_$orderNr.${exportFormat.fileExtension}", getExportedFileContent(exportFormat))

    fun submit() {
        state = state.submit()
    }

    fun pay() {
        state = state.pay()
    }

    fun cancel() {
        state = state.cancel()
    }

    fun markProvisional() {
        state = state.markProvisional()
    }
}
