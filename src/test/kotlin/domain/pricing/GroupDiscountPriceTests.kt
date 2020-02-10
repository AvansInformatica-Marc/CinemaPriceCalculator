package domain.pricing

import domain.MovieTicket
import domain.utils.getScreeningForWeekDay
import domain.utils.pricePerSeat
import java.time.DayOfWeek
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GroupDiscountPriceTests {
    @Test
    fun `Six or more people get a group discount of 10%`() {
        val testSizes = mapOf(
            5 to false,
            6 to true,
            7 to true
        )

        for ((ticketCount, hasDiscount) in testSizes) {
            // Arrange
            val tickets = Array(ticketCount) {
                MovieTicket(
                    movieScreening = getScreeningForWeekDay(
                        DayOfWeek.SATURDAY
                    ),
                    isStudentOrder = false,
                    isPremiumTicket = false,
                    seatRow = 1,
                    seatNr = 1
                )
            }

            // Act
            GroupDiscountTicketPricingStrategy().modifyTicketPrices(tickets.asIterable())

            // Assert
            assertTrue(tickets.all { it.calculatedPrice == if (hasDiscount) 2.25 else pricePerSeat })
        }
    }

    @Test
    fun `Students don't get group discounts`() {
        // Arrange
        val ticket = MovieTicket(
            movieScreening = getScreeningForWeekDay(
                DayOfWeek.SATURDAY
            ),
            isStudentOrder = true,
            isPremiumTicket = false,
            seatRow = 1,
            seatNr = 1
        )

        // Act
        GroupDiscountTicketPricingStrategy().modifyTicketPrice(ticket, 0, 7)

        // Assert
        assertEquals(
            actual = ticket.calculatedPrice,
            expected = pricePerSeat
        )
    }
}