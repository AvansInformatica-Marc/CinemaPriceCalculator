package domain.pricing

import domain.*
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.Month

class SecondTicketFreeTests {
    @Test
    fun `The second ticket for students is always free`() {
        // Arrange
        val tickets = Array(5) {
            MovieTicket(
                movieScreening = getScreeningForWeekDay(
                    DayOfWeek.SATURDAY
                ),
                isStudentOrder = true,
                isPremiumTicket = false,
                seatRow = 1,
                seatNr = 1
            )
        }

        // Act
        FreeSecondTicketPricingStrategy().modifyTicketPrices(tickets.asIterable())

        // Assert
        assertEquals(
            actual = tickets[0].calculatedPrice,
            expected = pricePerSeat
        )
        assertEquals(actual = tickets[1].calculatedPrice, expected = 0.0)
        assertEquals(
            actual = tickets[2].calculatedPrice,
            expected = pricePerSeat
        )
        assertEquals(actual = tickets[3].calculatedPrice, expected = 0.0)
        assertEquals(
            actual = tickets[4].calculatedPrice,
            expected = pricePerSeat
        )
    }

    @Test
    fun `The second ticket is always free from monday to thursday`() {
        val testList = mapOf(
            DayOfWeek.MONDAY to true,
            DayOfWeek.THURSDAY to true,
            DayOfWeek.FRIDAY to false,
            DayOfWeek.SUNDAY to false
        )

        for((dayOfWeek, isTicketFree) in testList) {
            // Arrange
            val tickets = Array(5) {
                MovieTicket(
                    movieScreening = getScreeningForWeekDay(
                        dayOfWeek
                    ),
                    isStudentOrder = false,
                    isPremiumTicket = false,
                    seatRow = 1,
                    seatNr = 1
                )
            }

            // Act
            FreeSecondTicketPricingStrategy().modifyTicketPrices(tickets.asIterable())

            // Assert
            assertEquals(
                actual = tickets[0].calculatedPrice,
                expected = pricePerSeat
            )
            assertEquals(
                actual = tickets[1].calculatedPrice,
                expected = if (isTicketFree) 0.0 else pricePerSeat
            )
            assertEquals(
                actual = tickets[2].calculatedPrice,
                expected = pricePerSeat
            )
            assertEquals(
                actual = tickets[3].calculatedPrice,
                expected = if (isTicketFree) 0.0 else pricePerSeat
            )
            assertEquals(
                actual = tickets[4].calculatedPrice,
                expected = pricePerSeat
            )
        }
    }
}
