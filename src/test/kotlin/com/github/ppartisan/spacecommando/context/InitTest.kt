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
        Init().process(input).shouldBeInstanceOf<Setup>()
    }

    @ParameterizedTest
    @ValueSource(strings = ["help", "h", "?"])
    fun `when input string is for help, then help`(input: String) {
        Init().process(input).shouldBeInstanceOf<Help>()
    }

    @ParameterizedTest
    @ValueSource(strings = ["quit", "q"])
    fun `when input string is quit, then quit`(input: String) {
        Init().process(input).shouldBeInstanceOf<Quit>()
    }

    @Test
    fun `when unrecognised input, then prompt invalid input`() {
        Init().process("nonsense").shouldBeInstanceOf<Invalid>()
    }

    @Test
    fun `when showing ui, then show init text`() {
        Init().ui() shouldEqual """
        Welcome to Alien vs. Commando! Here are your available commands:
            "start", "s"            -> Start a new game
            "help", "h", "?"        -> Show the help text
            "quit", "q"             -> Quit the game
        """.trimIndent()
    }

}