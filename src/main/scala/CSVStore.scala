import java.io.{File, PrintWriter}

import scala.io.Source

sealed class Data
object Input extends Data
object Intermediate extends Data

object CSVStore {

	private val prefix = "src/main/resources/"
	private val f1Path = prefix + "f1.csv"
	private val f2Path = prefix + "f2.csv"

	private val file1 = getData(f1Path)
	private val file2 = getData(f2Path)

	def getValue(file: Data)(index: Int): Option[Double] =
		file match {
			case Input if 0 to file1.length contains index =>
				Some(file1(index))
			case Intermediate if 0 to file2.length contains index =>
				Some(file2(index))
			case _ => None
		}

	def updateIntermediate(index: Int, value: Double): Unit = {
		file2(index) = value
		writeToFile(file2, f2Path)
	}

	private def writeToFile(array: Array[Double], path: String) = {
		val pw = new PrintWriter(new File(path))
		pw.write(array mkString ",")
		pw.close()
	}

	private def getData(path: String) =
		Source.fromFile(path)
			.getLines()
			.flatMap(_.split(","))
			.map(_.toDouble)
			.toArray
}
