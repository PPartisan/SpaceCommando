package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import org.junit.jupiter.api.assertInstanceOf
import kotlin.test.assertEquals
import kotlin.test.assertTrue

infix fun AppContext.shouldHaveCommandoAt(expected: Point) {
    assertEquals(expected, board.commando, "Commando should be at the specified position")
}

fun String.toPoint(): Point = split(",").let {
    Point(it[0].toInt(), it[1].toInt())
}

infix fun AppContext.shouldBeOnTurn(expected: Int) {
    assertInstanceOf<Turn>(this)
    assertEquals(expected, board.turn, "Should be on turn $expected")
}

infix fun AppContext.shouldHaveAlienInArea(area: Area) {
    assertTrue("Alien X-pos should be in area '$area'") { board.alien.isIn(area) }
}

fun Point.isIn(area: Area): Boolean =
    x in area.x && y in area.y

data class Area(val x: IntRange, val y: IntRange)