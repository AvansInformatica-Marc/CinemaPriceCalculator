package domain.pricing

import domain.MovieTicket

class PremiumTicketPricingStrategy : OrderPricingStrategy() {
    override fun modifyTicketPrice(ticket: MovieTicket, index: Int, totalAmountOfTickets: Int) {
        ticket.calculatedPrice += when {
            !ticket.isPremiumTicket -> 0
            ticket.isStudentOrder -> PREMIUM_STUDENT_TICKET_FEE
            else -> PREMIUM_TICKET_FEE
        }
    }

    companion object {
        const val PREMIUM_TICKET_FEE = 3
        const val PREMIUM_STUDENT_TICKET_FEE = 2
    }
}
