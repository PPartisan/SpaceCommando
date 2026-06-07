package com.github.ppartisan.spacecommando.context

import io.kotest.matchers.equals.shouldEqual
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.assume
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
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
    fun `when input is unrecognised, then transition to Invalid state`() = runTest {
        val commands = setOf("start", "s", "help", "h", "?", "quit", "q")
        val init = Init()
        checkAll(Arb.string()) {
            assume(it.lowercase() !in commands)
            init.process(it).shouldBeInstanceOf<Invalid>()
        }
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