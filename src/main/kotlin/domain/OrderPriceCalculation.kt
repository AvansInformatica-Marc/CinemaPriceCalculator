package domain

import java.time.DayOfWeek

const val premiumTicketFee = 3.0
const val premiumTicketFeeForStudents = 2.0
const val minimumAmountOfPeopleRequiredForGroupDiscount = 6

val daysWhereSecondTicketIsAlwaysFree =
    arrayOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY)

fun calculatePriceForTicket(totalAmountOfTickets: Int, ticketIndex: Int, ticket: MovieTicket): Double {
    var price = ticket.price

    // Adds premium ticket fee for premium tickets
    if(ticket.isPremiumTicket)
        price += if(ticket.isStudentOrder) premiumTicketFeeForStudents else premiumTicketFee

    // Adds 10% discount for groups
    if(!ticket.isStudentOrder && totalAmountOfTickets >= minimumAmountOfPeopleRequiredForGroupDiscount)
        price -= price * 0.1

    // Makes all even tickets free for students/on weekdays
    if(ticketIndex % 2 == 1 && (ticket.isStudentOrder || ticket.date.dayOfWeek in daysWhereSecondTicketIsAlwaysFree))
        price = 0.0

    return price
}