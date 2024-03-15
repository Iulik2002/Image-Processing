//Cozmac Iulian 325CD
//ID unic - 183425
import util.Pixel
import util.Util.{getNeighbors, toGrayScale}
import math.Fractional.Implicits.infixFractionalOps
import math.Integral.Implicits.infixIntegralOps
import math.Numeric.Implicits.infixNumericOps
import scala.annotation.tailrec
// Online viewer: https://0xc0de.fr/webppm/
object Solution {
  type Image = List[List[Pixel]]
  type GrayscaleImage = List[List[Double]]

  // prerequisites
  def fromStringPPM(image: List[Char]): Image = {
    //println(image)
    val lines = image.mkString.split("\n").map(_.trim)
    val dimensions = lines(1).split(" ").map(_.toInt)
    val data = lines.drop(3).flatMap(_.split(" ").map(_.toInt))
    data.grouped(3).map(group => Pixel(group(0), group(1), group(2)))
      .toList.grouped(dimensions(0)).map(_.toList).toList
  }

  def toStringPPM(image: Image): List[Char] = {
    val header = s"P3\n${image.head.length} ${image.length}\n255\n"
    val data = image.flatMap(_.map(pixel => s"${pixel.red} ${pixel.green} ${pixel.blue}"))
                  .grouped(1).map(_.mkString(" ")).mkString("\n") + "\n"
    header.toList ++ data.toList
  }

  // ex 1
  def verticalConcat(image1: Image, image2: Image): Image =
    image1 ++ image2

  // ex 2
  def horizontalConcat(image1: Image, image2: Image): Image =
    image1.zip(image2).map { case (row1, row2) => row1 ++ row2 }

  // ex 3
  def rotate(image: Image, degrees: Integer): Image = {
    degrees % 360 match {
      case 90 => image.transpose.reverse
      case 180 => image.transpose.reverse.transpose.reverse
      case 270 => image.transpose.reverse.transpose.reverse.transpose.reverse
      case 0 => image
    }
  }

  // ex 4
  val gaussianBlurKernel: GrayscaleImage = List[List[Double]](
    List( 1, 4, 7, 4, 1),
    List( 4,16,26,16, 4),
    List( 7,26,41,26, 7),
    List( 4,16,26,16, 4),
    List( 1, 4, 7, 4, 1)
  ).map(_.map(_ / 273))

  val Gx : GrayscaleImage = List(
    List(-1, 0, 1),
    List(-2, 0, 2),
    List(-1, 0, 1)
  )

  val Gy : GrayscaleImage = List(
    List( 1, 2, 1),
    List( 0, 0, 0),
    List(-1,-2,-1)
  )

  def applyConvolution(image: GrayscaleImage, kernel: GrayscaleImage): GrayscaleImage = {
    val radius = kernel.length/2
    val dotProduct  = getNeighbors(image, radius).map(_.map(matrix => {
            val zippedRows = matrix.zip(kernel)
            val products = zippedRows.map { case (row1, row2) =>
              row1.zip(row2).map { case (a, b) => a * b }.sum
            }
            products.sum
          }))
    dotProduct
  }

  def edgeDetection(image: Image, threshold: Double): Image = {
    val white_blackImage = image.map(_.map(toGrayScale))
    val blurredImage = applyConvolution(white_blackImage, gaussianBlurKernel)
    val Mx = applyConvolution(blurredImage, Gx)
    val My = applyConvolution(blurredImage, Gy)
    val combinedImage = Mx.zip(My).map((row1, row2) => row1.zip(row2).map({case (x, y) => math.abs(x) + math.abs(y)}))
    val finalImage = combinedImage.map(_.map(pixel => if (pixel < threshold) Pixel(0, 0, 0) else Pixel(255, 255, 255)))
    finalImage
  }

  // ex 5
  // functie ce formeaza o matrice cu Triunghiul lui Pascal
  def pascalTriangle(n: Int): List[List[BigInt]] = {
    if (n == 1) {
      List(List(1))
    } else {
      val prevTriangle = pascalTriangle(n - 1)
      val prevRow = prevTriangle.last
      val newRow = (BigInt(1) +: prevRow.zip(prevRow.tail).map(_ + _)) :+ BigInt(1)
      prevTriangle :+ newRow
    }
  }

  def moduloPascal(m: Integer, funct: Integer => Pixel, size: Integer): Image = {
    val pascal = pascalTriangle(size)
    val maxLength = pascal.last.length
    val zeros = List.fill(maxLength - 1)(funct(4))
    val image = pascal.map { row =>
      row.map { elem =>
        if (elem % m.toInt == 0) funct(0)
        else funct((elem % m.intValue()).toInt)
        } ++ zeros.take(maxLength - row.length)
    }
    image

  }
}
