package math.la

object Matrices {

  // Basic operations
  trait Operative[A] {
    def multiply(a: A, b: A): A
    def add(a: A, b: A): A
  }

  // Used to add more functions to base class
  implicit class OperativeOps[A](left: A)(implicit op: Operative[A]) {
    def *(right: A): A = op.multiply(left, right)
    def +(right: A): A = op.add(left, right)
  }

  // Add implementation for some types
  implicit object DoubleOp extends Operative[Double] {
    def multiply(x: Double, y: Double): Double = x * y
    def add(x: Double, y: Double): Double = x + y
  }

  implicit object IntOp extends Operative[Int] {
    def multiply(x: Int, y: Int): Int = x * y
    def add(x: Int, y: Int): Int = x + y
  }

  // Define matrix
  type Row = List[Double]
  type Matrix = List[Row]

  // Matrices
  trait MatrixOperative[A] {
    //Matrix transpose
    def t(m: A): A

    //Matrix multiplication
    def mXm(m1: A, m2: A): A
  }

  // Implementation for Matrix
  implicit object MatrixOp extends MatrixOperative[Matrix] {
    def t(m: Matrix): Matrix = {
      if (m.head.isEmpty) List()
      else m.map(_.head) :: t(m.map(_.tail))
    }

    def mXm(m1: Matrix, m2: Matrix): Matrix = {
      if (m1(1).length != m2.length)
        throw new Exception("These matrices doesn't meet matrix multiplication condition")

      for {
        m1row <- m1
      } yield for {
        m2col <- transpose(m2)
      } yield dotProduct(m1row, m2col)
      
    }
    
  }

  /**
   * dot product is a multiplication of each component from the both vectors added together
   */
  // Context bound
  def dotProduct[A: Operative](v1: List[A], v2: List[A]): A = {
    if (v1.length != v2.length) throw new Exception("The length of vectors are not equal")
    // Here * and + are defined in OperativePlus
    (v1 zip v2).map(e => e._1 * e._2).reduceLeft(_ + _)
  }

  /**
   * Transpose matrix
   */
  // Context bound
  def transpose[A: MatrixOperative](m: A): A = implicitly[MatrixOperative[A]].t(m)

  def mMultiplym[A: MatrixOperative](m1: A, m2: A): A = implicitly[MatrixOperative[A]].mXm(m1, m2)
  
}