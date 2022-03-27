package domain.states

import domain.OrderChangedMessage

class CreatedState(override var onOrderChanged: ((OrderChangedMessage) -> Unit)? = null) : EmptyOrderState() {
    override fun submit() = SubmittedState(onOrderChanged)

    override fun cancel() = CancelledState(onOrderChanged)
}
