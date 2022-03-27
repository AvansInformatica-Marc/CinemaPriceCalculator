package domain

import domain.TicketExportFormat.JSON
import domain.TicketExportFormat.PLAINTEXT
import domain.pricing.OrderPricingStrategy
import domain.states.OrderState
import domain.states.StateNotExpectedError
import domain.utils.writeToFile
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

data class Order(val orderNr: Int) : KoinComponent {
    private val tickets = mutableListOf<MovieTicket>()

    private val pricingStrategy by inject<OrderPricingStrategy>()

    private var state = get<OrderState>()

    var onOrderChanged: ((OrderChangedMessage) -> Unit)?
        get() = state.onOrderChanged
        set(value) {
            state.onOrderChanged = value
        }

    val price: Double
        get() {
            // Resets all ticket prices to their initial values
            for (ticket in tickets) {
                ticket.calculatedPrice = ticket.initialPrice
            }

            pricingStrategy.modifyTicketPrices(tickets)

            var totalPrice = 0.0

            for (ticket in tickets) {
                totalPrice += ticket.calculatedPrice
            }

            return totalPrice
        }

    fun addSeatReservation(ticket: MovieTicket) {
        tickets += ticket
    }

    @Throws(StateNotExpectedError::class)
    fun submit() {
        state = state.submit()
    }

    @Throws(StateNotExpectedError::class)
    fun pay() {
        state = state.pay()
    }

    @Throws(StateNotExpectedError::class)
    fun cancel() {
        state = state.cancel()
    }

    @Throws(StateNotExpectedError::class)
    fun markProvisional() {
        state = state.markProvisional()
    }

    fun getExportedFileContent(exportFormat: TicketExportFormat) = when (exportFormat) {
        JSON -> tickets.joinToString(",\n", "[\n", "\n]") { "\"$it\"" }
        PLAINTEXT -> tickets.joinToString("\n")
    }

    suspend fun export(exportFormat: TicketExportFormat) =
        writeToFile(
            "Order_$orderNr.${exportFormat.fileExtension}",
            getExportedFileContent(exportFormat)
        )

    override fun toString() =
        "Number: $orderNr\nPrice: $price\nTickets:\n${tickets.joinToString(",\n  ", "  ")}"
}
