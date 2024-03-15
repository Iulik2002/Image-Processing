case class Pixel(red: Integer, green: Integer, blue: Integer) {
}
type Image = List[List[Pixel]]
type GrayscaleImage = List[List[Double]]
//def pascalTriangle(n: Int): List[List[Int]] = {
//  if (n == 1) {
//    List(List(1))
//  } else {
//    val prevTriangle = pascalTriangle(n - 1)
//    val prevRow = prevTriangle.last
//    val newRow = 1 +: prevRow.zip(prevRow.tail).map(_ + _) :+ 1
//    prevTriangle :+ newRow
//  }
//}
//def moduloPascal(m: Integer, funct: Integer => Pixel, size: Integer): Image = {
//  val pascal = pascalTriangle(size)
//  val maxLength = pascal.last.length
//  val zeros = List.fill(maxLength - 1)(funct(-10))
//
//  val image = pascal.map { row =>
//    zeros.take(maxLength - row.length) ++ row.map { elem =>
//      if (elem % m == 0) funct(0)
//      else funct(elem % m)
//    }
//  }
//  image.map(_.reverse)
//
//}
//def pickColor(i: Integer): Pixel = {
//  if (i == 0) Pixel(255, 0, 0)
//  else if (i == 1) Pixel(0, 0, 255)
//  else if (i == 2) Pixel(0, 255, 0)
//  else if (i == 3) Pixel(255, 255, 255)
//  else Pixel(0, 0, 0)
//}
//moduloPascal(2, pickColor, 5).foreach(println)
val kernel: GrayscaleImage = List(List(1, 0, -1), List(1, 0, -1), List(1, 0, -1))
val bigMatrix : List[List[GrayscaleImage]] =  List(
  List(
    List(List(1, 1, 1), List(1, 3, 4), List(1, 4, 2)), List(List(1 , 1, 2), List(3, 4, 2), List(4, 2, 1)), List(List(1, 2, 5), List(4, 2, 5), List(2, 1, 6)), List(List(2, 5, 3), List(2, 5, 1), List(1, 6, 4)),
    List(List(1, 3, 4), List(1, 4, 2), List(1, 2, 4)), List(List(3, 4, 2), List(4, 2, 1), List(2, 4, 5)), List(List(4, 2, 5), List(2, 1, 6), List(4, 5, 6)), List(List(2, 5, 1), List(1 , 6, 4), List(5, 6, 1)),
    List(List(1, 4, 2), List(1, 2, 4), List(1, 3, 2)), List(List(4, 2, 1), List(2, 4, 5), List(3, 2, 1)), List(List(2, 1, 6), List(4, 5, 6), List(2, 1, 2)), List(List(1, 6, 4), List(5, 6, 1), List(1, 2, 4)),
    List(List(1, 2, 4), List(1, 3, 2), List(1, 3, 5)), List(List(2 , 4, 5), List(3, 2, 1), List(3, 5, 1)), List(List(4, 5, 6), List(2, 1, 2), List(5, 1, 4)), List(List(5 , 6, 1), List(1 , 2, 4), List(1, 4, 4))))
def applyConvolution(kernel: GrayscaleImage): GrayscaleImage = {
  val radius = kernel.length / 2
  val dotProduct = bigMatrix.map(_.map(matrix => {
    val zippedRows = matrix.zip(kernel)
    val dotProducts = zippedRows.map { case (row1, row2) =>
      row1.zip(row2).map { case (a, b) => a * b }.sum
    }
    dotProducts.sum
  }))
  dotProduct
}
applyConvolution(kernel)