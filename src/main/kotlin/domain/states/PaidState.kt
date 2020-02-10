package domain.states

import domain.OrderChangedMessage

class PaidState(override var onOrderChanged: ((OrderChangedMessage) -> Unit)? = null) : EmptyOrderState() {
    init {
        onOrderChanged?.invoke(OrderChangedMessage.OrderPaidMessage)
    }
}
