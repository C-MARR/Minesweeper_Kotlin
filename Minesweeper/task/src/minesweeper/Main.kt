package minesweeper

fun main() {
    Board.addRandomMinesAndGenerateHints()
    Board.printBoard()
    while (!Board.gameOver) {
        print("Set/unset mine marks or claim a cell as free: ")
        val input = readLine()!!.split(" ")
        println()
        when {
            input.contains("free") && input.size == 3 -> {
                try {
                    if (!Board.cellGrid[input[1].toInt() - 1][input[0].toInt() - 1].marked) {
                        Board.cellGrid[input[1].toInt() - 1][input[0].toInt() - 1].explore()
                    }
                    if (Board.gameOver) break
                } catch (e: Exception) {
                    Board.printBoard()
                    continue
                }
            }
            input.contains("mine") && input.size == 3 -> {
                try {
                    Board.cellGrid[input[1].toInt() - 1][input[0].toInt() - 1].mark()
                    Board.checkWinCondition()
                    if (Board.gameOver) break
                } catch (e: Exception) {
                    Board.printBoard()
                    continue
                }
            }
        }
        Board.printBoard()
    }
}
