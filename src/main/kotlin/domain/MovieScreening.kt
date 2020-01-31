package domain

import java.time.LocalDateTime

class MovieScreening(
    private val movie: Movie,
    private val dateAndTime: LocalDateTime,
    val pricePerSeat: Double
) {
    val date = dateAndTime.toLocalDate()

    init {
        movie.addScreening(this)
    }

    override fun toString() = "${movie.title} - $dateAndTime"
}