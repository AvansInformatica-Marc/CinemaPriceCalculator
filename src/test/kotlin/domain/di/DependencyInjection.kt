package domain.di

import domain.TestPricingStrategy
import domain.pricing.OrderPricingStrategy
import domain.states.CreatedState
import domain.states.OrderState
import org.koin.dsl.module

val testModule = module {
    single {
        CreatedState() as OrderState
    }

    single {
        TestPricingStrategy() as OrderPricingStrategy
    }
}
