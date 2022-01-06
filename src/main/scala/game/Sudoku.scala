package game

import scala.annotation.tailrec
import scala.util.Try

private class Sudoku(sudoku: Seq[Seq[Int]], correctValue: Int) {

  //Regexp of correct slot value
  val regexp = "([1-9])".r

  /** 
   * Function for starting recursion interation over columns and finally checking sum of column values on correct result
   *
   * @return [[scala.Boolean]] with `true` if result is correct
   * 
   * @throws IncorrectResultException if result is incorrect
   * @throws IncorrectLengthException if length is incorrect
   * @throws IncorrectValueException if value is incorrect
  */
  def resolve: Boolean = checkColumn (sudoku.foldLeft(Map.empty.withDefaultValue(0)) {
      case (acc, rec) => checkRow(rec, acc)
    })

  /** 
   * Function for finally compare sum of row/column values with `correctValue` for the correct result
   * 
   * @param currentValue sum of row/column values
   * @param columns [[scala.collection.immutable.Map]] contains sum of column values
   * 
   * @return [[scala.collection.immutable.Map]] contains sum of column values
   * 
   * @throws IncorrectResultException если судоку заполнено неверно
  */
  private def check(currentValue: Int, columns: Map[Int, Int]): Map[Int, Int] =
    currentValue match {
      case _ if currentValue == correctValue => columns 
      case _ => throw new IncorrectResultException
    }

  /**
   * function for checking slot value
   * 
   * @param value value for checking
   * 
   * @return [[scala.Int]]  checked value
   * 
   * @throws IncorrectValueException if value is incorrect
   */
  private def checkValue(value: Int) = value.toString match {
      case regexp(_) => value
      case _ => throw new IncorrectValueException
    }

  /** 
   * Fuction for recursive iteration over a series of row values and accumulation sum of columns values 
   * 
   * @param columns [[scala.collection.immutable.Map]] contains sum of column values
   * @param row current row iteration
   * 
   * @return [[scala.collection.immutable.Map]] contains sum of column values from current row iteration
   * 
   * @throws IncorrectResultException if result is incorrect
   * @throws IncorrectValueException if row length is incorrect
  */
  private def checkRow(row: Seq[Int], columns: Map[Int, Int]): Map[Int, Int] = {
    /** 
       * Function for summing column values
       * 
       * @param columns current sum of column values
       * @param row current row
       * @param rowIndex current index of row interation
       * 
       * @return [[scala.collection.immutable.Map]] contains sum of column values after accumulation
      */
    def sum(columns: Map[Int, Int], row: Seq[Int], rowIndex: Int): Map[Int, Int] = 
      columns + ((rowIndex,  columns(rowIndex) + row(rowIndex)))

    row match {
      case _ if row.length != sudoku.length => throw new IncorrectLengthException
      case _ => {
        val result = row.zipWithIndex.foldLeft(RowTransitionalResult.empty(columns)) {
          case (acc, (cur, rowIndex)) => RowTransitionalResult(acc.result + checkValue(cur), sum(acc.columns, row, rowIndex))
        }
        check(result.result, result.columns)
      }
    }
  }

  /** 
   * Function for recursive checking sum of column values
   * 
   * @param columns [[scala.collection.immutable.Map]] contains sum of column values
   * 
   * @return [[scala.Boolean]] with `true` if result is correct
   * 
   * @throws IncorrectResultException if result is incorrect
  */
  private def checkColumn(columns: Map[Int, Int]): Boolean =
    columns.foldLeft(columns) {
      case (acc, cur) => check(cur._2, acc)
    }.nonEmpty
}

//case class for saving transitional result of row recursion
case class RowTransitionalResult(result: Int, columns: Map[Int,Int]) 

//companion object for creation empty RowTransitionalResult
case object RowTransitionalResult {
  def empty(columns: Map[Int, Int]) = RowTransitionalResult(0, columns)
}

//companion object for encapsulation Sudoku costructor
object Sudoku {
  /** 
   * Function to find sum of number series by array length
   * 
   * @param sudoku current game wich will be verified 
   * 
   * @return [[scala.Int]] sum of number series
  */
  private def sum(sudoku: Seq[Seq[Int]]): Int = sudoku.zipWithIndex.foldLeft(0) {
    case (acc, (rows, columnIndex)) => acc + columnIndex + 1
  }

  def apply(sudoku: Seq[Seq[Int]]) = new Sudoku(sudoku, sum(sudoku))
}

//Exception for wrong sudoku solution
class IncorrectResultException extends RuntimeException

//Exception for wrong sudoku length
class IncorrectLengthException extends  RuntimeException

//Exception for wrong sudoku row value
class IncorrectValueException extends RuntimeException
