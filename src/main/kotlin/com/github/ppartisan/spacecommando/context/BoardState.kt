package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point


data class BoardState(
    val alien: Point,
    val commando: Point,
    val turn: Int
) {

    companion object {
        val initial = BoardState(
            alien = Point.NONE,
            commando = Point.NONE,
            turn = -1
        )
    }
}
