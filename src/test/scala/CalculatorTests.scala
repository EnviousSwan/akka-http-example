import org.scalatest.{Matchers, WordSpec}

class CalculatorTests extends WordSpec with Matchers {

	"Calculator" should {

		"check if sum of two is less than 10" in {
			Calculator.checkCondition(2, 4) shouldBe true
			Calculator.checkCondition(2, 4) shouldBe true
			Calculator.checkCondition(7, 3) shouldBe false
			Calculator.checkCondition(10, 12) shouldBe false
		}

		"return one parameter if < 10, subtract 10 otherwise" in {
			Calculator.calculate(5) shouldBe 5
			Calculator.calculate(-2) shouldBe -2
			Calculator.calculate(17) shouldBe 17 - 10
			Calculator.calculate(29) shouldBe 29 - 10
		}

		"return sum of two if sum > 10, add 10 otherwise" in {
			Calculator.calculate(7, 8) shouldBe 7 + 8
			Calculator.calculate(42, 37) shouldBe 42 + 37
			Calculator.calculate(2, 5) shouldBe 2 + 5 + 10
			Calculator.calculate(-2, -3) shouldBe -2 + (-3) + 10
		}

		"return 0 if applied to more than 2 arguments or none" in {
			Calculator.calculate() shouldBe 0
			Calculator.calculate(1, 1, 1) shouldBe 0
			Calculator.calculate(2, 5, 1, 10, 25) shouldBe 0
		}
	}
}
