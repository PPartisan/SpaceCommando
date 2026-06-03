package com.github.ppartisan.spacecommando.context

import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldEqual
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class InvalidTest {

    @ParameterizedTest
    @ValueSource(strings = ["", "not-whitespace-or-empty", "  ", "\n\t"])
    fun `when processing any input, then resume original context`(input: String) {
        val wrapped = Init()
        val invalid = Invalid(null, wrapped, wrapped.board)
        invalid.onProcess(input) shouldBeEqual wrapped
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "  ", "\n\t"])
    fun `when original input is blank, then show missing input message on ui`(input: String) {
        val invalid = Invalid(input, Init(), BoardState.initial)
        invalid.ui() shouldContain "<Missing Input>"
    }

    @Test
    fun `when original input is null, then show missing input message on ui`() {
        val invalid = Invalid(null, Init(), BoardState.initial)
        invalid.ui() shouldContain "<Missing Input>"
    }

    @Test
    fun `when original input is not blank, then show invalid input on ui`() {
        val invalid = Invalid("some_input", Init(), BoardState.initial)
        invalid.ui() shouldEqual """
        Invalid Input:
            some_input        
        Press ENTER to return to the previous option.
        """.trimIndent()
    }
}