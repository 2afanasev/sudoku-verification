import game.{IncorrectResultException, IncorrectValueException, IncorrectLengthException, Sudoku}

object TestMain extends App {

  val tests_incorrectResult = Seq(
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5)
  )

  val test_incorrectLengh = Seq(
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
  )

  val test_incorrectValue = Seq(
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(5, 8, 7, 4, 6, 9, 1, 2, 3),
    Seq(9, 2, 1, 3, 5, 7, 8, 6, 4),
    Seq(2, 4, 8, 6, 7, 1, 3, 5, 9),
    Seq(7, 5, 23, 2, 4, 3, 6, 1, 8),
    Seq(1, 3, 6, 5, 9, 8, 7, 4, 2),
    Seq(3, 7, 5, 9, 2, 6, 4, 8, 1),
    Seq(8, 1, 4, 7, 3, 5, 2, 9, 6),
    Seq(6, 9, 2, 8, 1, 4, 5, 3, 7)
  )

  val test_correctResult = Seq(
    Seq(4, 6, 3, 1, 8, 2, 9, 7, 5),
    Seq(5, 8, 7, 4, 6, 9, 1, 2, 3),
    Seq(9, 2, 1, 3, 5, 7, 8, 6, 4),
    Seq(2, 4, 8, 6, 7, 1, 3, 5, 9),
    Seq(7, 5, 9, 2, 4, 3, 6, 1, 8),
    Seq(1, 3, 6, 5, 9, 8, 7, 4, 2),
    Seq(3, 7, 5, 9, 2, 6, 4, 8, 1),
    Seq(8, 1, 4, 7, 3, 5, 2, 9, 6),
    Seq(6, 9, 2, 8, 1, 4, 5, 3, 7)
  )



  assert(try {
    Sudoku(tests_incorrectResult).resolve
  } catch {
    case _: IncorrectResultException => true
    case _ => false
  })

  assert(try {
    Sudoku(test_incorrectLengh).resolve
  } catch {
    case _: IncorrectLengthException => true
    case _ => false
  })

  assert(try {
    Sudoku(test_incorrectValue).resolve
  } catch {
    case _: IncorrectValueException => true
    case _ => false
  })

  assert(Sudoku(test_correctResult).resolve)
}
