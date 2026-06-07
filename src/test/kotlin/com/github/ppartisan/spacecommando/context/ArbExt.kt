package com.github.ppartisan.spacecommando.context

import com.github.ppartisan.spacecommando.Point
import com.github.ppartisan.spacecommando.Point.Companion.VALID_MOVES
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.choice
import io.kotest.property.arbitrary.element
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.stringPattern

fun Arb.Companion.invalidMoveInputs(): Arb<String> = Arb.bind(
    Arb.int(-100..100),
    Arb.int(-100..100),
    ::Point
).filter { it !in VALID_MOVES }.map { "${it.x},${it.y}" }

fun Arb.Companion.malformedDelimiter(): Arb<String> = Arb.stringPattern("\\d{1,2}[|/.;x-]\\d{1,2}")
fun Arb.Companion.malformedWordCoordinates(): Arb<String> = Arb.stringPattern("[a-zA-Z]{1,5},[a-zA-Z]{1,5}")
fun Arb.Companion.missingYAxis(): Arb<String> = Arb.stringPattern("\\d{1,2},?")
fun Arb.Companion.missingXAxis(): Arb<String> = Arb.stringPattern(",?\\d{1,2}")
fun Arb.Companion.whitespaceOnly(): Arb<String> = Arb.stringPattern("[ \n\t]{1,5}")

fun Arb.Companion.malformedCoordinateStrings(): Arb<String> = Arb.choice(
    malformedDelimiter(),
    malformedWordCoordinates(),
    missingXAxis(),
    missingYAxis(),
    whitespaceOnly()
)

fun Arb.Companion.invalidInputs(): Arb<String> = Arb.choice(
    Arb.invalidMoveInputs(), Arb.malformedCoordinateStrings()
)

fun Arb.Companion.validAlienMoves(): Arb<Point> =
    Arb.element(VALID_MOVES)

fun Arb.Companion.validAlienMoveStrings(): Arb<String> =
    Arb.validAlienMoves().map { "${it.x},${it.y}" }