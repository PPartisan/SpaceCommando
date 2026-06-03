package com.github.ppartisan.spacecommando.context

object Quit : AppContext(BoardState.initial) {

    override val isTerminal = true

    override fun onProcess(input: String): AppContext = this
    override fun ui(): String = "Exiting. Thanks for playing!"

}