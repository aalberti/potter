import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class PotterTest: StringSpec({
    "no book costs 0" {
        price() shouldBe 0.0
    }
    "1 book costs 8" {
        price(0) shouldBe 8.0
    }
    "1 book many times costs 8 every time" {
        price(0, 0, 0) shouldBe 8.0 * 3
    }
})
