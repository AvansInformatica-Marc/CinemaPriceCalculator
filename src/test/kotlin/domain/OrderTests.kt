package domain

import domain.*
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.Month

class OrderTests {
    @Test
    fun `calculatePrice adds all prices`() {
        // Arrange
        val order = Order(1)
        val amountOfTickets = 3
        val tickets = Array(amountOfTickets) {
            MovieTicket(
                movieScreening = getScreeningForWeekDay(
                    DayOfWeek.SATURDAY
                ),
                isStudentOrder = false,
                isPremiumTicket = false,
                seatRow = 1,
                seatNr = it
            )
        }

        for(ticket in tickets)
            order.addSeatReservation(ticket)

        // Act
        val price = order.calculatePrice()

        // Assert
        assertEquals(
            actual = price,
            expected = pricePerSeat * amountOfTickets
        )
    }
}
