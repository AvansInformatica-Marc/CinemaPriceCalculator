package domain.pricing

import domain.MovieTicket

class CombinedPricingStrategy(private val strategies: Array<OrderPricingStrategy>) : OrderPricingStrategy {
    override fun modifyTicketPrices(tickets: Iterable<MovieTicket>) {
        for (strategy in strategies) {
            strategy.modifyTicketPrices(tickets)
        }
    }
}
