package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import io.kotest.matchers.equals.shouldEqual
import org.junit.jupiter.api.Test

class GameWonTest {

    @Test
    fun `when game won, then print final player positions`() {
        GameWon(
            BoardState(
                alien = Point(10, 9),
                commando = Point(10, 10),
                turn = 10
            )
        ).ui() shouldEqual """
            You Won! The Alien is at '(10,9)', and the Commando is at '(10,10)'.
        """.trimIndent()
    }

}