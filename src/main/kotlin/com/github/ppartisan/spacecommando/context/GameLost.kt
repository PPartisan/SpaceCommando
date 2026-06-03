package com.github.ppartisan.spacecommando.context

class GameLost(board: BoardState) : AppContext(board) {

    override val isTerminal = true

    override fun onProcess(input: String): AppContext  = this

    override fun ui(): String =
        "You Lost! The Alien was at '${board.alien.printable()}', and the Commando was at '${board.commando.printable()}'."
}