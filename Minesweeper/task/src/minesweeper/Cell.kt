package minesweeper

class Cell(var explored: Boolean = false, var marked: Boolean = false,
           var mine: Boolean = false, var inProximity: Boolean = false) {
    private var display = '.'

    override fun toString(): String {
        return display.toString()
    }

    fun mark() {
        marked = !marked
    }

    fun updateDisplay() {
        this.display = when {
            marked -> '*'
            !explored -> '.'
            mine -> 'X'
            inProximity -> checkProximity()
            else -> '/'
        }
    }

    fun checkProximity(): Char {
        var num = 0
        var row = 0
        var column = 0
        if (this.mine) return '0'
        for (rows in Board.cellGrid.indices) {
            for (columns in Board.cellGrid[rows].indices) {
                if (this === Board.cellGrid[rows][columns]) {
                    row = rows
                    column = columns
                    break
                }
            }
        }
        for (rows in -1..1) {
            if (row + rows < 0 || row + rows >= Board.rows) continue
            for (columns in -1..1) {
                if (column + columns < 0 || column + columns >= Board.cells) {
                    continue
                } else if (Board.cellGrid[row + rows][column + columns].mine) {
                    num++
                }
            }
        }
        if (num > 0) this.inProximity = true
        return num.digitToChar()
    }

    fun explore() {
        if (this.inProximity) {
            this.explored = true
            return
        } else if(this.mine) {
            this.explored = true
            Board.gameOver = true
            Board.printBoard()
            println("You stepped on a mine and failed!\n")
            return
        }
        this.explored = true
        var row = 0
        var column = 0
        for (rows in Board.cellGrid.indices) {
            for (columns in Board.cellGrid[rows].indices) {
                if (this === Board.cellGrid[rows][columns]) {
                    row = rows
                    column = columns
                    break
                }
            }
        }
        for (rows in -1..1) {
            if (row + rows < 0 || row + rows >= Board.rows) continue
            for (columns in -1..1) {
                if (column + columns < 0 || column + columns >= Board.cells) continue
                    if (Board.cellGrid[row + rows][column + columns].mine) {
                        continue
                    }
                    else {
                        if (!Board.cellGrid[row + rows][column + columns].explored) {
                            if (Board.cellGrid[row + rows][column + columns].marked) {
                                Board.cellGrid[row + rows][column + columns].marked = false
                            }
                            Board.cellGrid[row + rows][column + columns].explored = true
                            Board.cellGrid[row + rows][column + columns].explore()
                        } else {
                            continue
                        }
                    }
            }
        }
    }
}