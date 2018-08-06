import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class PotterTest: StringSpec({
    "no book costs 0" {
        price() shouldBe 0.0
    }
})
