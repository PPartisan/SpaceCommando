package com.github.ppartisan.spacecommando.context

import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SetupTest {

    private lateinit var setup: Setup

    @BeforeEach
    fun setUp() {
        setup = Setup(BoardState.initial)
    }

    @ParameterizedTest
    @ValueSource(strings = ["10,0", "19,0", "10,19", "19,19", "15,10"])
    fun `when player gives valid start coords, then start coords for commando are retained`(input: String) {
        setup.process(input) shouldHaveCommandoAt input.toPoint()
    }

    @ParameterizedTest
    @ValueSource(strings = ["9,20", "9,-1", "10,100", "5,19", "15,20"])
    fun `when player gives out-of-range start coords, then transition to invalid input`(input: String) {
        setup.process(input).shouldBeInstanceOf<Invalid>()
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "  ", "10|10", "5", "five,ten"])
    fun `when player gives invalid input, then transition to invalid input`(input: String) {
        setup.process(input).shouldBeInstanceOf<Invalid>()
    }

    @ParameterizedTest
    @ValueSource(strings = ["10,0", "19,0", "10,19", "19,19", "15,10"])
    fun `when player gives valid start coords, then alien start coords are in valid start positions`(input: String) {
        setup.process(input) shouldHaveAlienInArea Area(x = (0..7), y = (0..19))
    }

    @ParameterizedTest
    @ValueSource(strings = ["10,0", "19,0", "10,19", "19,19", "15,10"])
    fun `when player gives valid start coords, then board moves to turn one`(input: String) {
        setup.process(input) shouldBeOnTurn 1
    }

}