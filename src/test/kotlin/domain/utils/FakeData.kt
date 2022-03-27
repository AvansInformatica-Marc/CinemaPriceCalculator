package domain.utils

import domain.Movie
import domain.MovieScreening
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.Month

val movie = Movie("Star Wars")
const val pricePerSeat = 2.50

fun getScreeningForWeekDay(dayOfWeek: DayOfWeek): MovieScreening {
    val screeningTime = LocalDateTime.of(2020, Month.FEBRUARY, dayOfWeek.value + 2, 20, 0)
    return MovieScreening(
        movie,
        screeningTime,
        pricePerSeat
    )
}