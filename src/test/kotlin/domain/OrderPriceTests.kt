package domain

import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.Month

class OrderPriceTests {
    @Test
    fun `Ticket price returns normal price when there's no discount`() {
        // Arrange
        val ticket = MovieTicket(
            movieScreening = getScreeningForWeekDay(DayOfWeek.SATURDAY),
            isStudentOrder = false,
            isPremiumTicket = false,
            seatRow = 1,
            seatNr = 1
        )

        // Act
        val price = calculatePriceForTicket(4, 3, ticket)

        // Assert
        assertEquals(actual = price, expected = pricePerSeat)
    }

    @Test
    fun `The second ticket for students is always free`() {
        // Arrange
        val ticket = MovieTicket(
            movieScreening = getScreeningForWeekDay(DayOfWeek.SATURDAY),
            isStudentOrder = true,
            isPremiumTicket = false,
            seatRow = 1,
            seatNr = 1
        )

        // Act
        val prices = Array(5) {
            calculatePriceForTicket(3, it, ticket)
        }

        // Assert
        assertEquals(actual = prices[0], expected = pricePerSeat)
        assertEquals(actual = prices[1], expected = 0.0)
        assertEquals(actual = prices[2], expected = pricePerSeat)
        assertEquals(actual = prices[3], expected = 0.0)
        assertEquals(actual = prices[4], expected = pricePerSeat)
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
            val ticket = MovieTicket(
                movieScreening = getScreeningForWeekDay(dayOfWeek),
                isStudentOrder = false,
                isPremiumTicket = false,
                seatRow = 1,
                seatNr = 1
            )

            // Act
            val prices = Array(5) {
                calculatePriceForTicket(3, it, ticket)
            }

            // Assert
            assertEquals(actual = prices[0], expected = pricePerSeat)
            assertEquals(actual = prices[1], expected = if(isTicketFree) 0.0 else pricePerSeat)
            assertEquals(actual = prices[2], expected = pricePerSeat)
            assertEquals(actual = prices[3], expected = if(isTicketFree) 0.0 else pricePerSeat)
            assertEquals(actual = prices[4], expected = pricePerSeat)
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
            val ticket = MovieTicket(
                movieScreening = getScreeningForWeekDay(DayOfWeek.SATURDAY),
                isStudentOrder = false,
                isPremiumTicket = false,
                seatRow = 1,
                seatNr = 1
            )

            // Act
            val prices = Array(ticketCount) {
                calculatePriceForTicket(ticketCount, it, ticket)
            }

            // Assert
            assertTrue(prices.all { it == if(hasDiscount) 2.25 else pricePerSeat })
        }
    }


    @Test
    fun `Students don't get group discounts`() {
        // Arrange
        val ticket = MovieTicket(
            movieScreening = getScreeningForWeekDay(DayOfWeek.SATURDAY),
            isStudentOrder = true,
            isPremiumTicket = false,
            seatRow = 1,
            seatNr = 1
        )

        // Act
        val price = calculatePriceForTicket(7, 0, ticket)

        // Assert
        assertEquals(actual = price, expected = pricePerSeat)
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