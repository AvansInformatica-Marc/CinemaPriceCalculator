package domain.states

abstract class EmptyOrderState : OrderState {
    override fun submit(): OrderState {
        throw StateNotExpectedError()
    }

    override fun pay(): OrderState {
        throw StateNotExpectedError()
    }

    override fun cancel(): OrderState {
        throw StateNotExpectedError()
    }

    override fun markProvisional(): OrderState {
        throw StateNotExpectedError()
    }
}
