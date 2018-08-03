import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.min

class PotterTest : StringSpec({
    val priceForOne = 8.0
    val priceForTwo = (priceForOne * 2) * 0.95
    val priceForThree = (priceForOne * 3) * .9
    val priceForFour = (priceForOne * 4) * .8
    val priceForFive = (priceForOne * 5) * .75

    "1 book is worth 8" {
        price("1" * 3) shouldBe priceForOne * 3
    }

    "1&2 get 5%" {
        price(listOf("1", "2")) shouldBe priceForTwo
    }

    "add remainder after discount" {
        price(listOf("1", "1", "2")) shouldBe priceForOne + priceForTwo
    }

    "apply several of the same discount" {
        price(listOf("1", "1", "2", "1", "2")) shouldBe priceForOne + priceForTwo * 2
    }

    "2&3 get 5%" {
        price(listOf("2", "3")) shouldBe priceForTwo
    }

    "1&2&3 get 10%" {
        price(listOf("1", "2", "3")) shouldBe priceForThree
    }

    "4-book set + 2" {
        price(listOf(0, 0, 1, 1, 2, 3)) shouldBe priceForFour + priceForTwo
    }

    "5-book set + 2" {
        price(listOf(0, 0, 1, 1, 2, 3, 5)) shouldBe priceForFive + priceForTwo
    }

    "2 4-book sets are cheaper than one 5-book set + one 3-book set" {
        price(listOf(
                0, 0,
                1, 1,
                2, 2,
                3,
                4)
        ) shouldBe priceForFour * 2
    }

    "optimize 3s and 5s as long as needed" {
        price(listOf(
                0, 0, 0, 0,
                1, 1, 1, 1,
                2, 2, 2, 2,
                3, 3,
                      4, 4)
        ) shouldBe priceForFour * 4
    }
})

operator fun String.times(times: Int): Collection<String> = List(times) { this }

private fun price(books: Collection<Any>): Double =
        sets(books, List(5) { 0 })
                .optimize()
                .mapIndexed { index, count -> count * bookSetPrice(index + 1) }
                .sum()

private tailrec fun sets(books: Collection<Any>, acc: List<Int>): List<Int> =
        if (books.isEmpty())
            acc
        else {
            val distinct = books.distinct()
            val newAcc = List(5) { i -> acc[i] + if (i == distinct.size - 1) 1 else 0 }
            sets(books.minusOneOfEach(distinct), newAcc)
        }

fun List<Int>.optimize(): List<Int> {
    val numberOfThreesAndFives = min(this[2], this[4])
    return listOf(
            this[0],
            this[1],
            this[2] - numberOfThreesAndFives,
            this[3] + 2 * numberOfThreesAndFives,
            this[4] - numberOfThreesAndFives
    )
}

private fun bookSetPrice(bookSetSize: Int): Double = when (bookSetSize) {
    5 -> 30.0
    4 -> 25.6
    3 -> 21.6
    2 -> 15.2
    else -> 8.0
}

private tailrec fun Collection<Any>.minusOneOfEach(toRemove: Collection<Any>): Collection<Any> = when (toRemove.isEmpty()) {
    true -> this
    else -> (this - toRemove.first()).minusOneOfEach(toRemove.drop(1))
}
