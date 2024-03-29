package domain

import domain.di.testModule
import domain.utils.KoinTest
import domain.utils.getScreeningForWeekDay
import java.time.DayOfWeek
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderExportTests : KoinTest(testModule) {
    @Test
    fun `Order without tickets exports empty text when export format is plaintext`(){
        // Arrange
        val order = Order(1)

        // Act
        val text = order.getExportedFileContent(TicketExportFormat.PLAINTEXT)

        // Assert
        assertEquals(actual = text, expected = "")
    }

    @Test
    fun `Order without tickets exports empty array when export format is json`(){
        // Arrange
        val order = Order(1)

        // Act
        val text = order.getExportedFileContent(TicketExportFormat.JSON)

        // Assert
        assertEquals(actual = text.replace("\n", "").trim(), expected = "[]")
    }

    @Test
    fun `Order with one ticket exports plaintext ticket toString() as single line`(){
        // Arrange
        val order = Order(1)
        val ticket = MovieTicket(
            movieScreening = getScreeningForWeekDay(DayOfWeek.SATURDAY),
            isStudentOrder = false,
            isPremiumTicket = false,
            seatRow = 1,
            seatNr = 1
        )
        order.addSeatReservation(ticket)

        // Act
        val text = order.getExportedFileContent(TicketExportFormat.PLAINTEXT)

        // Assert
        assertEquals(actual = text, expected = ticket.toString())
    }

    @Test
    fun `Order with one ticket exports json ticket toString() as array with single line`(){
        // Arrange
        val order = Order(1)
        val ticket = MovieTicket(
            movieScreening = getScreeningForWeekDay(DayOfWeek.SATURDAY),
            isStudentOrder = false,
            isPremiumTicket = false,
            seatRow = 1,
            seatNr = 1
        )
        order.addSeatReservation(ticket)

        // Act
        val text = order.getExportedFileContent(TicketExportFormat.JSON)

        // Assert
        assertEquals(actual = text.replace("\n", ""), expected = "[\"$ticket\"]")
    }
}
