package domain

sealed class OrderChangedMessage {
    object OrderSubmittedMessage : OrderChangedMessage()

    object PaymentReminderMessage : OrderChangedMessage()

    object OrderCancelledMessage : OrderChangedMessage()

    object OrderPaidMessage : OrderChangedMessage()
}
