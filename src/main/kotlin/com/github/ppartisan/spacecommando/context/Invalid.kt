package com.github.ppartisan.spacecommando.context

class Invalid(
    val originalInput: String?,
    val wrapped: AppContext,
    board: BoardState
) : AppContext(board) {

    override fun onProcess(input: String): AppContext = wrapped

    override fun ui(): String = """
        Invalid Input:
            ${originalInput?.ifBlank {"<Missing Input>" } }        
        Press ENTER to return to the previous option.
    """.trimIndent()

    companion object {
        fun AppContext.invalid(input: String?): Invalid =
            Invalid(input, this, board)
    }
}