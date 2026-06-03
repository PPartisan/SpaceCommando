package com.github.ppartisan.spacecommando.context

class GameWon(board: BoardState) : AppContext(board) {

    override val isTerminal = true
    override fun onProcess(input: String): AppContext  = this

    override fun ui(): String =
        "You Won! The Alien is at '${board.alien.printable()}', and the Commando is at '${board.commando.printable()}'."

}