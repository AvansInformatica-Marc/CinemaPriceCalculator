package domain.states

import domain.OrderChangedMessage

interface OrderState {
    var onOrderChanged: ((OrderChangedMessage) -> Unit)?

    @Throws(StateNotExpectedError::class)
    fun submit(): OrderState

    @Throws(StateNotExpectedError::class)
    fun pay(): OrderState

    @Throws(StateNotExpectedError::class)
    fun cancel(): OrderState

    @Throws(StateNotExpectedError::class)
    fun markProvisional(): OrderState
}
