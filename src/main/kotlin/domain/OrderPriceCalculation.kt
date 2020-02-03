package domain

import java.time.DayOfWeek.*

const val PREMIUM_TICKET_FEE = 3
const val PREMIUM_STUDENT_TICKET_FEE = 2
const val PEOPLE_REQUIRED_FOR_GROUP_DISCOUNT = 6
const val GROUP_DISCOUNT = 0.1

val daysWhereSecondTicketIsAlwaysFree = arrayOf(MONDAY, TUESDAY, WEDNESDAY, THURSDAY)

val MovieTicket.priceWithPremiumTicketFee
    get() = price + when {
        !isPremiumTicket -> 0
        isStudentOrder -> PREMIUM_STUDENT_TICKET_FEE
        else -> PREMIUM_TICKET_FEE
    }

fun calculatePriceForTicket(totalAmountOfTickets: Int, ticketIndex: Int, ticket: MovieTicket): Double {
    val isSecondTicket = ticketIndex.isOdd
    val isSecondTicketFree = ticket.isStudentOrder || ticket.date.dayOfWeek in daysWhereSecondTicketIsAlwaysFree
    val isLargeGroup = totalAmountOfTickets >= PEOPLE_REQUIRED_FOR_GROUP_DISCOUNT

    return when {
        isSecondTicket && isSecondTicketFree -> 0.0
        !ticket.isStudentOrder && isLargeGroup -> ticket.priceWithPremiumTicketFee * (1.0 - GROUP_DISCOUNT)
        else -> ticket.priceWithPremiumTicketFee
    }
}
