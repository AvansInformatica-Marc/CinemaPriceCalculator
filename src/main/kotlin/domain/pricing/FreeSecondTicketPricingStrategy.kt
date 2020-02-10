package domain.pricing

import domain.MovieTicket
import domain.utils.isOdd
import java.time.DayOfWeek

class FreeSecondTicketPricingStrategy : SingleOrderPricingStrategy() {
    override fun modifyTicketPrice(ticket: MovieTicket, index: Int, totalAmountOfTickets: Int) {
        val isSecondTicket = index.isOdd
        val isSecondTicketFree = ticket.isStudentOrder || ticket.date.dayOfWeek in DAYS_WITH_FREE_SECOND_TICKET

        if (isSecondTicket && isSecondTicketFree) {
            ticket.calculatedPrice = 0.0
        }
    }

    companion object {
        private val DAYS_WITH_FREE_SECOND_TICKET = arrayOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY
        )
    }
}
