package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import com.github.ppartisan.spacecommando.Point.Companion.VALID_MOVES
import com.github.ppartisan.spacecommando.context.Turn.MoveAlien
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.element
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

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

    @Test
    fun `when commando coordinates valid and within max move distance of board boundary, then move commando`() =
        runTest {
            checkAll(
                Arb.int(2..17),
                Arb.int(2..17),
                Arb.element(VALID_MOVES)
            ) { x, y, move ->
                val board = board.copy(commando = Point(x, y))
                Turn(board).process("${move.x},${move.y}") shouldHaveCommandoAt (board.commando + move)
            }
        }

    @Test
    fun `when commando coordinates valid, and move is off board left edge, then move commando to board edge only`() =
        runTest {
            checkAll(Arb.int(0..1), Arb.int(0..19)) { x, y ->
                Turn(
                    board.copy(commando = Point(x, y))
                ).process("-2,0") shouldHaveCommandoAt (Point(0, y))
            }
        }

    @Test
    fun `when commando coordinates valid, and move is off board right edge, then move commando to board edge only`() =
        runTest {
            checkAll(Arb.int(18..19), Arb.int(0..19)) { x, y ->
                Turn(
                    board.copy(commando = Point(x, y))
                ).process("2,0") shouldHaveCommandoAt (Point(19, y))
            }
        }

    @Test
    fun `when commando coordinates valid, and move is off board top edge, then move commando to board edge only`() =
        runTest {
            checkAll(Arb.int(0..19), Arb.int(18..19)) { x, y ->
                Turn(
                    board.copy(commando = Point(x, y))
                ).process("0,2") shouldHaveCommandoAt (Point(x, 19))
            }
        }

    @Test
    fun `when commando coordinates valid, and move is off board bottom edge, then move commando to board edge only`() =
        runTest {
            checkAll(Arb.int(0..19), Arb.int(0..1)) { x, y ->
                Turn(
                    board.copy(commando = Point(x, y))
                ).process("0,-2") shouldHaveCommandoAt (Point(x, 0))
            }
        }

    @Test
    fun `when commando coordinates invalid, then prompt invalid input`() = runTest {
        checkAll(Arb.invalidInputs()) {
            Turn(board).process(it).shouldBeInstanceOf<Invalid>()
        }
    }

    @RepeatedTest(50)
    fun `when commando coordinates valid, then move alien within allowed move space`() = runTest {
        checkAll(Arb.validAlienMoveStrings()) { commandoMove ->
            Turn(
                board.copy(alien = Point(5, 5))
            ).process(commandoMove) shouldHaveAlienInArea Area(x = (3..7), y = (3..7))
        }
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
            board.copy(alien = Point(10, 10), commando = Point(10, 10)),
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