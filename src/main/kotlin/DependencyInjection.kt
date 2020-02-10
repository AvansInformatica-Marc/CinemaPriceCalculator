import domain.pricing.*
import domain.states.CreatedState
import domain.states.OrderState
import org.koin.dsl.module

val mainModule = module {
    single {
        CreatedState() as OrderState
    }

    single {
        CombinedPricingStrategy(arrayOf(
            PremiumTicketPricingStrategy(),
            GroupDiscountTicketPricingStrategy(),
            FreeSecondTicketPricingStrategy()
        )) as OrderPricingStrategy
    }
}
