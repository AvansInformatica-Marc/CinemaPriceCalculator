package domain.pricing

import domain.MovieTicket

class GroupDiscountTicketPricingStrategy : SingleOrderPricingStrategy() {
    override fun modifyTicketPrice(ticket: MovieTicket, index: Int, totalAmountOfTickets: Int) {
        if (!ticket.isStudentOrder && totalAmountOfTickets >= PEOPLE_REQUIRED_FOR_GROUP_DISCOUNT) {
            ticket.calculatedPrice *= 1.0 - GROUP_DISCOUNT
        }
    }

    companion object {
        private const val PEOPLE_REQUIRED_FOR_GROUP_DISCOUNT = 6
        private const val GROUP_DISCOUNT = 0.1
    }
}
