package domain

import java.time.DayOfWeek

const val premiumTicketFee = 3
const val premiumTicketFeeForStudents = 2
const val minimumAmountOfPeopleRequiredForGroupDiscount = 6
const val groupDiscount = 0.1

val daysWhereSecondTicketIsAlwaysFree =
    arrayOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY)

val MovieTicket.priceWithPremiumTicketFee
    get() = price + when {
        !isPremiumTicket -> 0
        isStudentOrder -> premiumTicketFeeForStudents
        else -> premiumTicketFee
    }

fun calculatePriceForTicket(totalAmountOfTickets: Int, ticketIndex: Int, ticket: MovieTicket): Double {
    val isSecondTicket = ticketIndex.isOdd
    val isSecondTicketFree = ticket.isStudentOrder || ticket.date.dayOfWeek in daysWhereSecondTicketIsAlwaysFree
    val isLargeGroup = totalAmountOfTickets >= minimumAmountOfPeopleRequiredForGroupDiscount

    return when {
        isSecondTicket && isSecondTicketFree -> 0.0
        !ticket.isStudentOrder && isLargeGroup -> ticket.priceWithPremiumTicketFee * (1.0 - groupDiscount)
        else -> ticket.priceWithPremiumTicketFee
    }
}
