fun price(books: Collection<Any>): Double {
    val twoBookSet = books.twoBookSet()
    return if (twoBookSet != null)
        15.2 + price(books.minusAll(twoBookSet))
    else
        8.0 * books.size
}

private fun Collection<Any>.twoBookSet(): Collection<Any>? {
    val distinct = distinct()
    return if (distinct.size < 2) null
    else distinct.take(2)
}

fun Collection<Any>.minusAll(toRemove:Collection<Any>): Collection<Any> = when {
    toRemove.isEmpty() -> this
    else -> (this - toRemove.first()).minusAll(toRemove.drop(1))
}