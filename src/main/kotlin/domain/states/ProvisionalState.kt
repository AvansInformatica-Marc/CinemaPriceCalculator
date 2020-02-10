package domain.states

import domain.OrderChangedMessage

class ProvisionalState(override var onOrderChanged: ((OrderChangedMessage) -> Unit)? = null) : EmptyOrderState() {
    init {
        onOrderChanged?.invoke(OrderChangedMessage.PaymentReminderMessage)
    }

    override fun pay() = PaidState(onOrderChanged)

    override fun cancel() = CancelledState(onOrderChanged)
}
