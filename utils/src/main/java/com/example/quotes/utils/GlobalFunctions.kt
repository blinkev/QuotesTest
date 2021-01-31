package com.example.quotes.utils

fun <A, B, R> ifNotNull(a: A?, b: B?, func: (A, B) -> R): R? {
    return if (a != null && b != null) {
        func.invoke(a, b)
    } else null
}

fun <A, B, C, D, R> ifNotNull(a: A?, b: B?, c: C?, d: D?, func: (A, B, C, D) -> R): R? {
    return if (a != null && b != null && c != null && d != null) {
        func.invoke(a, b, c, d)
    } else null
}