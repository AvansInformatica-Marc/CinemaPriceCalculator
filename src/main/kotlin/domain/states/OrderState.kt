package domain.states

open class OrderState {
    open fun submit(): OrderState {
        throw StateNotExpectedError()
    }

    open fun pay(): OrderState {
        throw StateNotExpectedError()
    }

    open fun cancel(): OrderState {
        throw StateNotExpectedError()
    }

    open fun markProvisional(): OrderState {
        throw StateNotExpectedError()
    }
}
