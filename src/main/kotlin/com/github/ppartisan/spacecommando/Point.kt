package com.github.ppartisan.spacecommando

data class Point(val x: Int, val y: Int) {
    companion object {
        val NONE = Point(-1, -1)
        val ZERO = Point(0, 0)
    }
}