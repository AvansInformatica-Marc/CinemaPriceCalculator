package domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.Month

class OrderPriceTests {
    @Test
    fun `Ticket price returns normal price when there's no discount`() {
        // Arrange
        val movieScreening = getScreeningForWeekDay(DayOfWeek.SATURDAY)

        val tickets = Array(3) {
            MovieTicket(movieScreening, false, 1, it)
        }

        val order = Order(1, false)
        for(ticket in tickets)
            order.addSeatReservation(ticket)

        // Act
        val effectivePricesPerTicket = order.getEffectivePricesPerTicket()

        // Assert
        tickets.forEach {
            assertEquals(effectivePricesPerTicket[it] ?: -1.0, pricePerSeat, 0.0)
        }
    }

    @Test
    fun `The second ticket for students is always free`() {
        // Arrange
        val movieScreening = getScreeningForWeekDay(DayOfWeek.SATURDAY)

        val tickets = Array(5) {
            MovieTicket(movieScreening, false, 1, it)
        }

        val order = Order(2, true)
        for(ticket in tickets)
            order.addSeatReservation(ticket)

        // Act
        val effectivePricesPerTicket = order.getEffectivePricesPerTicket()

        // Assert
        for(ticket in tickets) {
            val isEven = tickets.indexOf(ticket) == 1 || tickets.indexOf(ticket) == 3
            val expectedPrice = if(isEven) 0.0 else pricePerSeat
            assertEquals(effectivePricesPerTicket[ticket] ?: -1.0, expectedPrice, 0.0)
        }
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
            val movieScreening = getScreeningForWeekDay(dayOfWeek)

            val tickets = Array(5) {
                MovieTicket(movieScreening, false, 1, it)
            }

            val order = Order(2, false)
            for(ticket in tickets)
                order.addSeatReservation(ticket)

            // Act
            val effectivePricesPerTicket = order.getEffectivePricesPerTicket()

            // Assert
            for(ticket in tickets) {
                val isEven = tickets.indexOf(ticket) == 1 || tickets.indexOf(ticket) == 3
                val expectedPrice = if(isEven && isTicketFree) 0.0 else pricePerSeat
                assertEquals(effectivePricesPerTicket[ticket] ?: -1.0, expectedPrice, 0.0)
            }
        }
    }

    @Test
    fun `Six or more people get a group discount of 10%`() {
        val testSizes = mapOf(
            5 to false,
            6 to true,
            7 to true
        )

        for((ticketCount, hasDiscount) in testSizes) {
            // Arrange
            val movieScreening = getScreeningForWeekDay(DayOfWeek.SATURDAY)

            val tickets = Array(ticketCount) {
                MovieTicket(movieScreening, false, 1, it)
            }

            val order = Order(4, false)
            for(ticket in tickets)
                order.addSeatReservation(ticket)

            // Act
            val prices = order.getEffectivePricesPerTicket()

            // Assert
            assertTrue(prices.all { it.value == if(hasDiscount) 2.25 else 2.50 })
        }
    }

    companion object {
        private val movie = Movie("Star Wars")
        private const val pricePerSeat = 2.50

        fun getScreeningForWeekDay(dayOfWeek: DayOfWeek): MovieScreening {
            val screeningTime = LocalDateTime.of(2020, Month.FEBRUARY, dayOfWeek.value + 2, 20, 0)
            return MovieScreening(movie, screeningTime, pricePerSeat)
        }
    }
}