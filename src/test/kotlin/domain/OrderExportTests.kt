package domain

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

class OrderExportTests {
    @Test
    fun `Order without tickets exports empty text when export format is plaintext`(){
        // Arrange
        val order = Order(1)

        // Act
        val text = order.getExportedFileContent(TicketExportFormat.PLAINTEXT)

        // Assert
        assertEquals(text, "")
    }

    @Test
    fun `Order without tickets exports empty array when export format is json`(){
        // Arrange
        val order = Order(1)

        // Act
        val text = order.getExportedFileContent(TicketExportFormat.JSON)

        // Assert
        assertEquals(text.replace("\n", "").trim(), "[]")
    }
}