package math.la

import math.la.Matrices;

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MatricesTest extends FunSuite {
  test("Dot Product of type Double") {
    type Row = List[Double]
    type Matrix = List[Row]
    
    val v1 = List(1.0, 2.0)
    val v2 = List(3.0, 4.0)
    assert(Matrices.dotProduct(v1, v2) === 11.0)
  }
  
  test("Dot Product of type Int") {
    val v1 = List(1, 2)
    val v2 = List(3, 4)
    assert(Matrices.dotProduct(v1, v2) === 11)
  }
  
  test("Transpose of Matrix") {
    val v1 = List(1, 2, 3)
    val v2 = List(4, 5, 6)
    val v3 = List(v1, v2).map(_.map(_.toDouble))
    
    val v4 = List(1, 4)
    val v5 = List(2, 5)
    val v6 = List(3, 6)
    val v7 = List(v4, v5, v6).map(_.map(_.toDouble))
    assert(Matrices.transpose(v3) === v7)
  }
  
  test("Multiplication of Matrix") {
    val v11 = List(1, 2, 3)
    val v12 = List(4, 5, 6)
    val v1 = List(v11, v12).map(_.map(_.toDouble))
    
    val v21 = List(7, 8)
    val v22 = List(9, 10)
    val v23 = List(11, 12)
    val v2 = List(v21, v22, v23).map(_.map(_.toDouble))
    
    val v31 = List(58, 64)
    val v32 = List(139, 154)
    val v3 = List(v31, v32)
    assert(Matrices.mMultiplym(v1, v2) === v3)
  }

}