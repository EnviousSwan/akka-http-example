import org.scalatest.{Matchers, PrivateMethodTester, WordSpec}

import scala.io.Source
import scala.util.Random

class CSVStoreTests extends WordSpec with Matchers {

	trait Context {
		val path = "src/main/resources/"
		val f1Path: String = path + "f1.csv"
		val f2Path: String = path + "f2.csv"

		def getData(path: String): Array[Double] =
			Source.fromFile(path)
				.getLines()
				.flatMap(_.split(","))
				.map(_.toDouble)
				.toArray
	}

	"CSVStore" should {

		"return value of the file by index" in new Context {
			val input: Array[Double] = getData(f1Path)
			val intermediate: Array[Double] = getData(f2Path)

			CSVStore.getValue(Intermediate)(5) shouldBe Some(intermediate(5))
			CSVStore.getValue(Input)(23) shouldBe Some(input(23))
		}

		"return None if index is out of bounds" in new Context {
			CSVStore.getValue(Intermediate)(Int.MinValue) shouldBe None
			CSVStore.getValue(Intermediate)(Int.MaxValue) shouldBe None
			CSVStore.getValue(Input)(Int.MaxValue) shouldBe None
			CSVStore.getValue(Input)(Int.MinValue) shouldBe None
		}

		"update value in Intermediate file by index" in new Context {
			val value: Double = Random.nextDouble * 20
			val index: Int = Random.nextInt(100)

			CSVStore.updateIntermediate(index, value)
			getData(f2Path)(index) shouldBe value
		}
	}
}
