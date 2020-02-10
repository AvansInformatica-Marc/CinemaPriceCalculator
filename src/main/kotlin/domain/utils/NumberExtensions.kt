package domain.utils

inline val Int.isEven
    get() = this % 2 == 0

inline val Int.isOdd
    get() = this % 2 == 1
