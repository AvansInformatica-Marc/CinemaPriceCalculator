package domain.pricing

import domain.MovieTicket

interface OrderPricingStrategy {
    fun modifyTicketPrices(tickets: Iterable<MovieTicket>)
}
