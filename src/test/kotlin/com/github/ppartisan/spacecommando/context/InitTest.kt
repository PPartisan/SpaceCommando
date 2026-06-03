package com.github.ppartisan.spacecommando.context

import io.kotest.matchers.equals.shouldEqual
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class InitTest {

    @ParameterizedTest
    @ValueSource(strings = ["start", "s"])
    fun `when input string is for setup, then setup`(input: String) {
        Init().onProcess(input).shouldBeInstanceOf<Setup>()
    }

    @Test
    fun `when unrecognised input, then prompt invalid input`() {
        Init().onProcess("nonsense").shouldBeInstanceOf<Invalid>()
    }

    @Test
    fun `when showing ui, then show init text`() {
        Init().ui() shouldEqual """
        Welcome to Alien vs. Commando! Here are your available commands:
            "start", "s"    -> Start a new game
            "help", "?"     -> Show the help text
            "quit", "q"     -> Quit the game
        """.trimIndent()
    }
}