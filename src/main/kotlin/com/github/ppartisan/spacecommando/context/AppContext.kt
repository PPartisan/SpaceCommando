package com.github.ppartisan.spacecommando.context

sealed class AppContext(
    val board: BoardState
) {

    open val isTerminal = false

    fun process(input: String?) : AppContext =
        when(input.lowercaseOrEmpty()) {
            "help", "h", "?" -> this as? Help ?: Help(this, board)
            "quit", "q" -> Quit
            else -> onProcess(input.lowercaseOrEmpty())
        }

    abstract fun onProcess(input: String): AppContext
    abstract fun ui(): String

    companion object {
        private fun String?.lowercaseOrEmpty() =
            orEmpty().lowercase()
    }

}