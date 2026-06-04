package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import io.kotest.matchers.equals.shouldEqual
import org.junit.jupiter.api.Test

class GameLostTest {

    @Test
    fun `when game lost, then print final player positions`() {
        GameLost(
            BoardState(
                alien = Point(5, 5),
                commando = Point(10, 10),
                turn = 10
            )
        ).ui() shouldEqual """
            You Lost! The Alien was at '(5,5)', and the Commando was at '(10,10)'.
        """.trimIndent()
    }

}