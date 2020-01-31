package domain

class MovieTicket(
    private val movieScreening: MovieScreening,
    val isPremiumTicket: Boolean,
    private val seatRow: Int,
    private val seatNr: Int
) {
    val price = movieScreening.pricePerSeat

    val date = movieScreening.date

    override fun toString() =
        "$movieScreening - row $seatRow, seat $seatNr${if (isPremiumTicket) " (Premium)" else ""}"
}