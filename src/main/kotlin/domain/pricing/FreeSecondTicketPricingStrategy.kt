package domain.pricing

import domain.MovieTicket
import domain.isOdd
import java.time.DayOfWeek

class FreeSecondTicketPricingStrategy : OrderPricingStrategy() {
    override fun modifyTicketPrice(ticket: MovieTicket, index: Int, totalAmountOfTickets: Int) {
        val isSecondTicket = index.isOdd
        val isSecondTicketFree = ticket.isStudentOrder || ticket.date.dayOfWeek in daysWhereSecondTicketIsAlwaysFree

        if(isSecondTicket && isSecondTicketFree) {
            ticket.calculatedPrice = 0.0
        }
    }

    companion object {
        val daysWhereSecondTicketIsAlwaysFree = arrayOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY
        )
    }
}