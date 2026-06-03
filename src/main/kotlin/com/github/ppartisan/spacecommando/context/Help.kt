package com.github.ppartisan.spacecommando.context

class Help(
    val wrapped: AppContext,
    board: BoardState
) : AppContext(board) {

    override fun onProcess(input: String): AppContext = wrapped

    override fun ui(): String = """
==================================================
           SPACE COMMANDO - GAME MANUAL           
==================================================

THE MISSION
The player is a Commando, trying to hunt an Alien
(AI-player) hiding on a 20x20 grid.

CO-ORDINATE SYSTEM
All inputs must be entered in the exact format:
    x,y
Grid limits: x and y must be between 0 and 19.

STARTING POSITIONS
Alien:    Starts on the left side of the grid.
Commando: Starts on the right side of the grid.

For the Commando/Player starting position:
    x >= 10 and x <= 19
    y >= 0  and y <= 10

MOVEMENT RULES
Enter your move as relative co-ordinates: (x,y)
You can move up to 2 squares total per turn.
* Valid moves: (2,0), (0,-2), (1,-1), (-1,1)
* Invalid move: (2,2) - total distance exceeds 2.
(Rule: absolute x + absolute y must be 0, 1, or 2)

If you try to move beyond the bounds of the playing
field, then your movement will be clamped to the
board edge.

WINNING THE GAME
The terminal will display the current distance 
between players after each round. If the distance 
is less than 1.5 units, the Commando wins!
==================================================

Press any key to return to the previous option.
    """.trimIndent()
}