package domain.states

class ProvisionalState : OrderState() {
    override fun pay() = PaidState()
    override fun cancel() = CancelledState()
}