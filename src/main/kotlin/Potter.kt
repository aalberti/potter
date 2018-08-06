import kotlin.math.min

fun price(books: Collection<Any>): Double {
    return if (books.isEmpty())
        0.0
    else {
        sets(books)
                .optimize()
                .mapIndexed { index, count -> setPrice(index + 1) * count }
                .sum()
    }
}

private fun sets(books:Collection<Any>): List<Int> = sets(books, List(5) {0})
private tailrec fun sets(books:Collection<Any>, acc: List<Int>): List<Int> {
    return if (books.isEmpty())
        acc
    else {
        val distinct = books.distinct()
        sets(books.minusAll(distinct), List(5) {index -> if (index == distinct.size - 1) acc[index] + 1 else acc[index]})
    }
}
private tailrec fun Collection<Any>.minusAll(toRemove:Collection<Any>): Collection<Any> = when {
    toRemove.isEmpty() -> this
    else -> (this - toRemove.first()).minusAll(toRemove.drop(1))
}
private fun List<Int>.optimize(): List<Int> {
    val threesAndFives = min(this[2], this[4])
    return listOf(this[0], this[1], this[2] - threesAndFives, this[3] + 2* threesAndFives, this[4] - threesAndFives)
}

private fun setPrice(setSize: Int): Double = when (setSize) {
    5 -> 30.0
    4 -> 25.6
    3 -> 21.6
    2 -> 15.2
    else -> 8.0
}
