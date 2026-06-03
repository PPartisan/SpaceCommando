package com.github.ppartisan.spacecommando

import com.github.ppartisan.spacecommando.context.AppContext
import com.github.ppartisan.spacecommando.context.Init
import java.util.concurrent.atomic.AtomicReference

fun main() {
    val current = AtomicReference<AppContext>(Init())
    do {
        val next = current.updateAndGet {
            print("${it.ui()}\n$ > ")
            it.process(readlnOrNull())
        }
        clearScreen()
        if (next.isTerminal)
            println(next.ui())
    } while (!next.isTerminal)
}

private fun clearScreen() =
    println("\u001b[H\u001b[2J")
