package com.github.ppartisan.spacecommando

data class Point(val x: Int, val y: Int) {
    companion object {
        val NONE = Point(-1, -1)
        val ZERO = Point(0, 0)

        val VALID_MOVES = listOf(
            Point(0, 0),
            Point(1, 0),
            Point(-1, 0),
            Point(0, 1),
            Point(0, -1),
            Point(1, 1),
            Point(1, -1),
            Point(-1, 1),
            Point(-1, -1),
            Point(2, 0),
            Point(-2, 0),
            Point(0, 2),
            Point(0, -2)
        )
    }
}