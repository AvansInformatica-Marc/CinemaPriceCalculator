package domain

enum class TicketExportFormat {
    PLAINTEXT, JSON
}

val TicketExportFormat.fileExtension
    get() = when(this) {
        TicketExportFormat.PLAINTEXT -> "txt"
        TicketExportFormat.JSON -> "json"
    }