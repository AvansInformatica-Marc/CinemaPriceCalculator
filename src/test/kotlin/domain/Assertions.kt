package domain

import org.junit.jupiter.api.Assertions

fun <T> assertEquals(actual: T, expected: T) = Assertions.assertEquals(expected, actual)

fun assertTrue(expected: Boolean) = Assertions.assertTrue(expected)

fun assertFalse(expected: Boolean) = Assertions.assertFalse(expected)
