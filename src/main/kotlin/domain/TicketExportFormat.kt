package domain

enum class TicketExportFormat {
    PLAINTEXT, JSON
}

inline val TicketExportFormat.fileExtension
    get() = when(this) {
        TicketExportFormat.PLAINTEXT -> "txt"
        TicketExportFormat.JSON -> "json"
    }
