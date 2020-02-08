package domain.states

class SubmittedState : OrderState() {
    override fun pay() = PaidState()
    override fun cancel() = CancelledState()
    override fun markProvisional() = ProvisionalState()
}