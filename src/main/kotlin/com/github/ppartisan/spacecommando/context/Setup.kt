package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import com.github.ppartisan.spacecommando.context.Invalid.Companion.invalid

class Setup(board: BoardState) : AppContext(board) {

    override fun onProcess(input: String): AppContext =
        input.parseCoordinates()
            ?.takeIf { it.isValidStart() }
            ?.let { Turn(board.copy(alien = alienStart(), commando = it, turn = 1)) }
            ?: invalid(input)

    override fun ui(): String = """
        Enter your starting position:
    """.trimIndent()

    companion object {
        private fun alienStart() : Point =
            Point((0..7).random(), (0..19).random())
        fun Point.isValidStart(): Boolean =
            (x in 10..19) && (y in 0..19)
    }
}