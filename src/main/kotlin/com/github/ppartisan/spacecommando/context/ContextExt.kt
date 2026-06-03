package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point

fun Pair<Int, Int>.toPoint() =
    Point(this.first, this.second)

fun String?.parseCoordinates(): Point? = runCatching {
    orEmpty()
        .split(",")
        .map { it.trim().toInt() }
        .let { it[0] to it[1] }
        .toPoint()
}.getOrNull()

operator fun Point.plus(other: Point) =
    Point(x + other.x, y + other.y)
