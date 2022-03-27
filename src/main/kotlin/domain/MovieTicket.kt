package domain

data class MovieTicket(
    private val movieScreening: MovieScreening,
    val isStudentOrder: Boolean,
    val isPremiumTicket: Boolean,
    private val seatRow: Int,
    private val seatNr: Int
) {
    val initialPrice = movieScreening.pricePerSeat

    var calculatedPrice = movieScreening.pricePerSeat

    val date = movieScreening.date

    override fun toString() =
        "$movieScreening - row $seatRow, seat $seatNr${if (isPremiumTicket) " (Premium)" else ""}"
}
