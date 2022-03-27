package domain

import domain.di.testModule
import domain.utils.KoinTest
import domain.utils.getScreeningForWeekDay
import java.time.DayOfWeek
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderTests : KoinTest(testModule) {
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
        val price = order.price

        // Assert
        assertEquals(
            actual = price,
            expected = TestPricingStrategy.PRICE * amountOfTickets
        )
    }
}
