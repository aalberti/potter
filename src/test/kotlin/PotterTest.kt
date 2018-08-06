import io.kotlintest.forAll
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class PotterTest : StringSpec({
    val priceForOne = 8.0
    val priceForTwo = priceForOne * 2 * .95
    val priceForThree = priceForOne * 3 * .9
    val priceForFour = priceForOne * 4 * .8
    val priceForFive = priceForOne * 5 * .75

    "no book costs 0" {
        price(listOf()) shouldBe 0.0
    }
    "1 book costs 8" {
        price(listOf(0)) shouldBe priceForOne
    }
    "1 book many times costs 8 every time" {
        price(listOf(0, 0, 0)) shouldBe priceForOne * 3
    }
    "2-book set gets 5% discount" {
        forAll(listOf(
                listOf(1, 2),
                listOf(0, 1),
                listOf("Harry", "Potter")
        )) {
            price(it) shouldBe priceForTwo
        }
    }
    "2-book set many times gets 5% discount every time" {
        price(listOf(
                1, 2,
                1, 2,
                1, 2
        )) shouldBe priceForTwo * 3
    }
    "3-book set gets 10% discount" {
        price(listOf(1, 2, 3)) shouldBe priceForThree
    }
    "4-book set gets 20% discount" {
        price(listOf(1, 2, 3, 4)) shouldBe priceForFour
    }
    "5-book set gets 25% discount" {
        price(listOf(1, 2, 3, 4, 5)) shouldBe priceForFive
    }
    "2+2+1" {
        price(listOf("1", "1", "2", "1", "2")) shouldBe priceForOne + priceForTwo * 2
    }
    "4+2" {
        price(listOf(0, 0, 1, 1, 2, 3)) shouldBe priceForFour + priceForTwo
    }
    "5+2" {
        price(listOf(0, 0, 1, 1, 2, 3, 5)) shouldBe priceForFive + priceForTwo
    }
})
