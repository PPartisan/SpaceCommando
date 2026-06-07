package com.github.ppartisan.spacecommando.context

import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain
import io.kotest.property.Arb
import io.kotest.property.arbitrary.Codepoint
import io.kotest.property.arbitrary.string
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.arbitrary.whitespace
import io.kotest.property.assume
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class InvalidTest {

    @Test
    fun `when processing any non-global input, then resume the original context`() = runTest {
        val wrapped = Init()
        val invalid = Invalid(null, wrapped, wrapped.board)
        val globalCommands = setOf("help", "h", "?", "quit", "q")

        checkAll(Arb.string()) {
            assume(it.lowercase() !in globalCommands)
            invalid.process(it) shouldBeEqual wrapped
        }
    }

    @Test
    fun `when original input is blank, then show missing input message on ui`() = runTest {
        checkAll(Arb.string(minSize = 0, maxSize = 20, codepoints = Codepoint.whitespace())) {
            Invalid(
                it, Init(), BoardState.initial
            ).ui() shouldContain "<Missing Input>"
        }
    }

    @Test
    fun `when original input is null, then show missing input message on ui`() {
        val invalid = Invalid(null, Init(), BoardState.initial)
        invalid.ui() shouldContain "<Missing Input>"
    }

    @Test
    fun `when original input is not blank, then show invalid input on ui`() = runTest {
        checkAll(Arb.stringPattern("\\S{1,10}")) {
            Invalid(
                it, Init(), BoardState.initial
            ).ui() shouldContain it
        }
    }

}