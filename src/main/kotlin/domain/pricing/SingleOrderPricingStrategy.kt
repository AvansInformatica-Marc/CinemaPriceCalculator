package domain.pricing

import domain.MovieTicket

abstract class SingleOrderPricingStrategy : OrderPricingStrategy {
    override fun modifyTicketPrices(tickets: Iterable<MovieTicket>) {
        tickets.forEachIndexed { index, ticket ->
            modifyTicketPrice(ticket, index, tickets.count())
        }
    }

    abstract fun modifyTicketPrice(ticket: MovieTicket, index: Int, totalAmountOfTickets: Int)
}
