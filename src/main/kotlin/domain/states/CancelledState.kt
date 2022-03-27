package domain.states

import domain.OrderChangedMessage

class CancelledState(override var onOrderChanged: ((OrderChangedMessage) -> Unit)? = null) : EmptyOrderState() {
    init {
        onOrderChanged?.invoke(OrderChangedMessage.OrderCancelledMessage)
    }
}
