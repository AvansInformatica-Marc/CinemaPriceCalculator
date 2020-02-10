package domain

import domain.pricing.OrderPricingStrategy

class TestPricingStrategy : OrderPricingStrategy {
    override fun modifyTicketPrices(tickets: Iterable<MovieTicket>) {
        for(ticket in tickets) {
            ticket.calculatedPrice = PRICE
        }
    }

    companion object {
        const val PRICE = 12.34
    }
}