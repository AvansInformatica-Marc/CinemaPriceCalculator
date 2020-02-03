package domain

import java.time.LocalDate
import java.time.LocalDateTime

data class MovieScreening(
    private val movie: Movie,
    private val dateAndTime: LocalDateTime,
    val pricePerSeat: Double
) {
    val date = dateAndTime.toLocalDate() as LocalDate

    init {
        movie.addScreening(this)
    }

    override fun toString() = "${movie.title} - $dateAndTime"
}
