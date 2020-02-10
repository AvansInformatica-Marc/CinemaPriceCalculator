package domain.states

import domain.OrderChangedMessage

class SubmittedState(override var onOrderChanged: ((OrderChangedMessage) -> Unit)? = null) : EmptyOrderState() {
    init {
        onOrderChanged?.invoke(OrderChangedMessage.OrderSubmittedMessage)
    }

    override fun pay() = PaidState(onOrderChanged)

    override fun cancel() = CancelledState(onOrderChanged)

    override fun markProvisional() = ProvisionalState(onOrderChanged)
}
