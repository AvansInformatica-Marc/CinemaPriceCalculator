package domain.states

class CreatedState : OrderState() {
    override fun submit() = SubmittedState()
    override fun cancel() = CancelledState()
}