package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import com.github.ppartisan.spacecommando.context.Invalid.Companion.invalid
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Turn(board: BoardState) : AppContext(board) {

    override fun onProcess(input: String): AppContext =
        input.parseCoordinates()
            ?.takeIfValidMove()
            ?.moveCommando()
            ?: invalid(input)

    private fun Point?.moveCommando() : AppContext? =
        this?.let { moveCommandoBy(it) }

    private fun moveCommandoBy(moveBy: Point): AppContext {
        val nextBoard = board.copy(
            alien = board.moveAlien(),
            commando = board.moveBy(moveBy),
            turn = board.nextTurn()
        )

        return when {
            nextBoard.isAlienFound() -> GameWon(nextBoard)
            nextBoard.isGameOver() -> GameLost(nextBoard)
            else -> Turn(nextBoard)
        }
    }

    override fun ui(): String = """
${board.pingAlien()}

Your current position is ${board.commando.printable()}.
The Alien is ${board.distanceToAlien().printable()} units away from you.
        
Enter your move:
""".trimIndent()

    companion object {
        private fun BoardState.moveAlien(): Point = with(alien) {
            Point(
                (x + (-1..1).random()).coerceIn(0, 19),
                (y + (-1..1).random()).coerceIn(0, 19)
            )
        }

        private fun Point?.takeIfValidMove() : Point? =
            this?.takeIf { it.isValidMove() }

        private fun BoardState.nextTurn(): Int =
            turn + 1

        private fun BoardState.isGameOver(): Boolean =
            turn >= 10

        private fun BoardState.moveBy(moveBy: Point): Point = with(commando) {
            ((x + moveBy.x).coerceIn(0, 19) to (y + moveBy.y).coerceIn(0, 19)).toPoint()
        }

        private fun Double.printable(): String = String.format("%.2f", this)

        private fun Point.isValidMove(): Boolean =
            (abs(x) + abs(y)) <= 2

        private fun BoardState.distanceSqToAlien(): Int =
            (commando.x - alien.x) * (commando.x - alien.x) +
                    (commando.y - alien.y) * (commando.y - alien.y)

        private fun BoardState.distanceToAlien(): Double =
            sqrt(distanceSqToAlien().toDouble())

        private fun BoardState.isAlienFound(): Boolean =
            distanceToAlien() < 1.5

        private fun BoardState.pingAlien(): String = buildString {
            val targetDistSq = distanceSqToAlien()

            for (y in 19 downTo 0) {
                for (x in 0..19) {
                    val cellDistSq = (x - commando.x) * (x - commando.x) +
                            (y - commando.y) * (y - commando.y)

                    when {
                        x == commando.x && y == commando.y -> append("X ")
                        cellDistSq == targetDistSq -> append("A ")
                        else -> append(". ")
                    }
                }
                append("\n")
            }
            append("\n")
        }
    }
}