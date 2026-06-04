package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.context.Invalid.Companion.invalid

class Init : AppContext(BoardState.initial) {

    override fun onProcess(input: String): AppContext = when(input) {
        "start", "s" -> Setup(board)
        else -> invalid(input)
    }

    override fun ui(): String = """
        Welcome to Alien vs. Commando! Here are your available commands:
            "start", "s"            -> Start a new game
            "help", "h", "?"        -> Show the help text
            "quit", "q"             -> Quit the game
    """.trimIndent()
}