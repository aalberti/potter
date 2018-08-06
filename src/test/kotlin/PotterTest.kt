import io.kotlintest.forAll
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class PotterTest : StringSpec({
    val priceForOne = 8.0
    val priceForTwo = priceForOne * 2 * .95

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
})
