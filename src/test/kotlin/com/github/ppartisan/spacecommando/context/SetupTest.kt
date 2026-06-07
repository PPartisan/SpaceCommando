package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.assume
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SetupTest {

    private lateinit var setup: Setup

    @BeforeEach
    fun setUp() {
        setup = Setup(BoardState.initial)
    }

    @Test
    fun `when player gives valid start coords, then start coords for commando are retained`() = runTest {
        checkAll(Arb.int(10..19), Arb.int(0..19)) { x, y ->
            setup.process("$x,$y") shouldHaveCommandoAt Point(x, y)
        }
    }

    @Test
    fun `when player gives out-of-range start coords, then transition to invalid input`() = runTest {
        checkAll(Arb.int(-50..50), Arb.int(-50..50)) { x, y ->
            assume(atLeastOneOutOfRangeStartCoordinate(x, y))
            setup.process("$x,$y").shouldBeInstanceOf<Invalid>()
        }
    }

    @Test
    fun `when player gives invalid input, then transition to invalid input`() = runTest {
        checkAll(Arb.malformedCoordinateStrings()) {
            setup.process(it).shouldBeInstanceOf<Invalid>()
        }
    }

    @Test
    fun `when player gives valid start coords, then alien start coords are in valid start positions`() = runTest {
        checkAll(Arb.int(10..19), Arb.int(0..19)) { x, y ->
            setup.process("$x,$y") shouldHaveAlienInArea Area(0..7, 0..19)
        }
    }

    @Test
    fun `when player gives valid start coords, then board moves to turn one`() = runTest {
        checkAll(Arb.int(10..19), Arb.int(0..19)) { x, y ->
            setup.process("$x,$y") shouldBeOnTurn 1
        }
    }

    companion object {
        private fun atLeastOneOutOfRangeStartCoordinate(x: Int, y: Int) =
            x !in 10..19 || y !in 0..19
    }

}