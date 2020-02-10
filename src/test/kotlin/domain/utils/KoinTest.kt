package domain.utils

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

open class KoinTest(val module: Module) : KoinTest {
    @BeforeTest
    fun before() {
        startKoin {
            modules(module)
        }
    }

    @AfterTest
    fun after() {
        stopKoin()
    }
}