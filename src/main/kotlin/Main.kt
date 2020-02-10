import domain.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.time.LocalDateTime
import java.time.Month

fun main() {
    startKoin {
        modules(mainModule)
    }

    val movie = Movie("Star Wars")
    val screening = MovieScreening(
        movie = movie,
        dateAndTime = LocalDateTime.of(2020, Month.FEBRUARY, 21, 20, 0),
        pricePerSeat = 2.50
    )
    val order = Order(1)

    for (ticketNumber in 1 until 5) {
        order.addSeatReservation(
            MovieTicket(
                movieScreening = screening,
                isStudentOrder = false,
                isPremiumTicket = false,
                seatRow = 1,
                seatNr = ticketNumber
            )
        )
    }

    println("Order:\n---\n$order")

    order.onOrderChanged = {
        println(when(it) {
            is OrderChangedMessage.OrderSubmittedMessage -> "Order is submitted."
            is OrderChangedMessage.OrderCancelledMessage -> "Order is cancelled."
            is OrderChangedMessage.PaymentReminderMessage -> "Remember to pay your order!"
            is OrderChangedMessage.OrderPaidMessage -> "Order is payed."
        })
    }

    order.submit()
    order.markProvisional()
    order.cancel()

    stopKoin()
}