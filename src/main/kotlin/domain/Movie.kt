package domain

class Movie(val title: String) {
    private val screenings = mutableListOf<MovieScreening>()

    fun addScreening(screening: MovieScreening) {
        screenings += screening
    }
}