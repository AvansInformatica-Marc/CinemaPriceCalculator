package domain.pricing

import domain.*
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.Month

class PremiumTicketPriceTests {
    @Test
    fun `Premium ticket fee is not added when ticket is not premium`(){
        // Arrange
        val ticket = MovieTicket(
            movieScreening = getScreeningForWeekDay(
                DayOfWeek.SATURDAY
            ),
            isStudentOrder = false,
            isPremiumTicket = false,
            seatRow = 1,
            seatNr = 1
        )

        // Act
        PremiumTicketPricingStrategy().modifyTicketPrice(ticket, 0, 1)

        // Assert
        assertEquals(
            actual = ticket.calculatedPrice,
            expected = pricePerSeat
        )
    }

    @Test
    fun `Premium ticket fee for students is added when ticket is premium and a student order`(){
        // Arrange
        val ticket = MovieTicket(
            movieScreening = getScreeningForWeekDay(
                DayOfWeek.SATURDAY
            ),
            isStudentOrder = true,
            isPremiumTicket = true,
            seatRow = 1,
            seatNr = 1
        )

        // Act
        PremiumTicketPricingStrategy().modifyTicketPrice(ticket, 0, 1)

        // Assert
        assertEquals(
            actual = ticket.calculatedPrice,
            expected = pricePerSeat + PremiumTicketPricingStrategy.PREMIUM_STUDENT_TICKET_FEE
        )
    }

    @Test
    fun `Premium ticket fee is added when ticket is premium but not a student order`(){
        // Arrange
        val ticket = MovieTicket(
            movieScreening = getScreeningForWeekDay(
                DayOfWeek.SATURDAY
            ),
            isStudentOrder = false,
            isPremiumTicket = true,
            seatRow = 1,
            seatNr = 1
        )

        // Act
        PremiumTicketPricingStrategy().modifyTicketPrice(ticket, 0, 1)

        // Assert
        assertEquals(
            actual = ticket.calculatedPrice,
            expected = pricePerSeat + PremiumTicketPricingStrategy.PREMIUM_TICKET_FEE
        )
    }
}
