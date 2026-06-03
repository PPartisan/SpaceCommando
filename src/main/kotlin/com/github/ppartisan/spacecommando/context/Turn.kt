package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import com.github.ppartisan.spacecommando.context.Invalid.Companion.invalid
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Turn(board: BoardState) : AppContext(board) {

    override fun onProcess(input: String): AppContext = when {
        board.isGameOver() -> GameLost(board)
        board.isAlienFound() -> GameWon(board)
        else -> input.parseCoordinates()
            ?.takeIfValidMove()
            ?.moveCommando()
            ?: invalid(input)
    }

    private fun Point?.moveCommando() : Turn? =
        this?.let { moveCommandoBy(it) }

    private fun moveCommandoBy(moveBy: Point) =
        Turn(board.copy(alien = board.moveAlien(), commando = board.moveBy(moveBy), turn = board.nextTurn()))

    override fun ui(): String = """
        Your current position is ${board.commando.printable()}.
        The Alien is ${board.distanceToAlien().printable()} units away from you.
        
        Enter your move:
    """.trimIndent()

    companion object {
        private fun BoardState.moveAlien(): Point = with(alien) {
            Point(x + (-1..1).random(), y + (-1..1).random())
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

        private fun BoardState.distanceToAlien(): Double =
            sqrt((commando.x - alien.x).toDouble().pow(2) + (commando.y - alien.y).toDouble().pow(2))

        private fun Double.printable(): String = String.format("%.2f", this)

        private fun BoardState.isAlienFound(): Boolean =
            distanceToAlien() < 1.5

        private fun Point.isValidMove(): Boolean =
            (abs(x) + abs(y)) <= 2

    }
}

