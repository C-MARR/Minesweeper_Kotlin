package minesweeper

object Board {
    var gameOver = false
    const val rows = 9
    const val cells = 9
    var numberOfMines = 10
    val cellGrid = List(rows) { List(cells) { Cell() } }

    fun printBoard() {
        if (gameOver) {
            cellGrid.flatten().filter { it.mine }.forEach { it.explored = true }
        }
            cellGrid.flatten().forEach { it.updateDisplay() }
            print(" │"); (1..cells).forEach(::print); print("│\n")
            print("—│"); repeat(cells) { print("—") }; print("│\n")
            for (row in 1..rows) {
                print("${row}│")
                cellGrid[row - 1].forEach { it.updateDisplay(); print(it) }
                print("│\n")
            }
            print("—│"); repeat(cells) { print("—") }; print("│\n")
    }

    fun addRandomMinesAndGenerateHints() {
        while (cellGrid.flatten().count { it.mine } < numberOfMines) {
            cellGrid.flatten().random().mine = true
        }

        cellGrid.flatten().forEach {
            it.checkProximity()
        }
    }

    fun checkWinCondition() {
        if (numberOfMines == cellGrid.flatten().filter { it.marked }.size &&
                numberOfMines == cellGrid.flatten().filter { it.mine && it.marked }.size ||
                cellGrid.flatten().filterNot { it.mine }.all { it.explored }) {
            gameOver = true
            println("Congratulations! You found all the mines!")
        }
    }

}