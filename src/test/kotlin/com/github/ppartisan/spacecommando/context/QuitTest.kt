package com.github.ppartisan.spacecommando.context

import io.kotest.matchers.equals.shouldEqual
import org.junit.jupiter.api.Test

class QuitTest {

    @Test
    fun `when quitting game, then print thanks for playing text`() {
        Quit.ui() shouldEqual "Exiting. Thanks for playing!"
    }

}