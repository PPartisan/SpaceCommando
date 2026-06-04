package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import com.github.ppartisan.spacecommando.context.Turn.MoveAlien
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TurnTest {

    private lateinit var board: BoardState

    @BeforeEach
    fun setUp() {
        board = BoardState(
            alien = Point(5, 5),
            commando = Point(10, 10),
            turn = 1
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["0,0", "1,1", "2,0", "-2,0", "0,-2", "-1,-1"])
    fun `when commando coordinates valid, then move commando`(input: String) {
        Turn(board).process(input) shouldHaveCommandoAt (board.commando + input.toPoint())
    }

    @Test
    fun `when commando coordinates valid, and move is off board left edge, then move commando to board edge only`() {
        Turn(
            board.copy(commando = Point(1, 10))
        ).process("-2,0") shouldHaveCommandoAt (Point(0, 10))
    }

    @Test
    fun `when commando coordinates valid, and move is off board right edge, then move commando to board edge only`() {
        Turn(
            board.copy(commando = Point(18, 10))
        ).process("2,0") shouldHaveCommandoAt (Point(19, 10))
    }

    @Test
    fun `when commando coordinates valid, and move is off board top edge, then move commando to board edge only`() {
        Turn(
            board.copy(commando = Point(10, 18))
        ).process("0,2") shouldHaveCommandoAt (Point(10, 19))
    }

    @Test
    fun `when commando coordinates valid, and move is off board bottom edge, then move commando to board edge only`() {
        Turn(
            board.copy(commando = Point(10, 1))
        ).process("0,-2") shouldHaveCommandoAt (Point(10, 0))
    }

    @ParameterizedTest
    @ValueSource(strings = ["10,10", "1,11", "200,200", "-20,0", "0,-25", "-11,-11"])
    fun `when commando coordinates invalid, then prompt invalid input`(input: String) {
        Turn(board).process(input).shouldBeInstanceOf<Invalid>()
    }

    @RepeatedTest(50)
    fun `when commando coordinates valid, then move alien within allowed move space`() {
        Turn(
            board.copy(alien = Point(5, 5))
        ).process("0,0") shouldHaveAlienInArea Area(x = (3..7), y = (3..7))
    }

    @Test
    fun `when turn, the print scanner and current position to ui`() {
        Turn(board).process("0,0").ui().shouldContainInOrder(
            "|===============SCANNER===============|",
            "=======================================",
            "Your current position is (10,10).",
            "The Alien is",
            "units away from you."
        )
    }

    @Test
    fun `when commando move to within alien detection distance, then transition to game won`() {
        Turn(
            board.copy(alien = Point(10,10), commando = Point(10,10)),
            MoveAlien(Point.ZERO)
        ).process("0,0").shouldBeInstanceOf<GameWon>()
    }

    @Test
    fun `when current turn is turn 9, then next turn ends game with loss`() {
        Turn(
            board.copy(turn = 9),
        ).process("0,0").shouldBeInstanceOf<GameLost>()
    }
}