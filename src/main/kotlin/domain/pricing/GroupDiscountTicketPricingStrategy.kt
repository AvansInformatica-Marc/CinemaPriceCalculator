package domain.pricing

import domain.MovieTicket

class GroupDiscountTicketPricingStrategy : OrderPricingStrategy() {
    override fun modifyTicketPrice(ticket: MovieTicket, index: Int, totalAmountOfTickets: Int) {
        if(!ticket.isStudentOrder && totalAmountOfTickets >= PEOPLE_REQUIRED_FOR_GROUP_DISCOUNT) {
            ticket.calculatedPrice *= 1.0 - GROUP_DISCOUNT
        }
    }

    companion object {
        const val PEOPLE_REQUIRED_FOR_GROUP_DISCOUNT = 6
        const val GROUP_DISCOUNT = 0.1
    }
}
