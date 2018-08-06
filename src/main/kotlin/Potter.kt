fun price(books: Collection<Any>): Double {
    return if (books.isEmpty())
        0.0
    else {
        val set = books.distinct()
        val setPrice = when (set.size) {
            3 -> 21.6
            2 -> 15.2
            else -> 8.0
        }
        setPrice + price(books.minusAll(set))
    }
}

fun Collection<Any>.minusAll(toRemove:Collection<Any>): Collection<Any> = when {
    toRemove.isEmpty() -> this
    else -> (this - toRemove.first()).minusAll(toRemove.drop(1))
}
