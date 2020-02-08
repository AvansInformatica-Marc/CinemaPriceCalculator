package domain.pricing

import domain.MovieTicket

class CombinedPricingStrategy : OrderPricingStrategy() {
    override fun modifyTicketPrice(ticket: MovieTicket, index: Int, totalAmountOfTickets: Int) {
        for(strategy in STRATEGIES) {
            strategy.modifyTicketPrice(ticket, index, totalAmountOfTickets)
        }
    }

    companion object {
        val STRATEGIES = arrayOf(
            PremiumTicketPricingStrategy(),
            GroupDiscountTicketPricingStrategy(),
            FreeSecondTicketPricingStrategy()
        )
    }
}