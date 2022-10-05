package connectfour

class Board(val rows: Int, val columns: Int) {
    private val structure: MutableList<MutableList<Char>> = MutableList(columns) { mutableListOf() }

    fun isColumnOutOfRange(column: Int): Boolean {
        if (column !in 1..columns) {
            println("The column number is out of range (1 - $columns)")
            return true
        }

        return false
    }

    fun isColumnFull(column: Int): Boolean {
        if (structure[column - 1].size >= rows) {
            println("Column $column is full")
            return true
        }

        return false
    }

    fun isFull(): Boolean {
        // Check if all the columns are full
        for (column in structure) {
            if (column.size != rows) return false
        }

        return true
    }

    fun addDisk(player: Player, columnNumber: Int) {
        structure[columnNumber - 1].add(player.disk)
    }

    fun clear() {
        for (column in structure) {
            column.clear()
        }
    }

    fun isPlayerWon(player: Player, columnIndex: Int): Boolean {
        val disc = player.disk

        // Check vertical
        var counter = 0
        for (elem in structure[columnIndex].asReversed()) {
            counter++

            if (elem != disc) {
                break
            }

            if (counter >= 4) return true
        }

        val rowIndex = structure[columnIndex].lastIndex

        // Check horizontal
        counter = 0
        for (column in structure) {
            counter++

            if (column.lastIndex < rowIndex || column[rowIndex] != disc) {
                counter = 0
            }

            if (counter >= 4) return true
        }

        // Check right diagonal
        counter = 0
        val rightDiagonalCoords = when {
            columnIndex > rowIndex -> listOf(columnIndex - rowIndex, 0)
            rowIndex > columnIndex -> listOf(0, rowIndex - columnIndex)
            else -> listOf(0, 0)
        }
        var j = rightDiagonalCoords[1]
        for (i in rightDiagonalCoords[0] until structure.size) {
            if (j > rows - 1) break

            counter++

            if (structure[i].lastIndex < j || structure[i][j] != disc) {
                counter = 0
            }

            if (counter >= 4) return true

            j++
        }

        // Check left diagonal
        counter = 0
        val leftDiagonalCoords = when {
            columnIndex + rowIndex >= rows - 1 -> listOf(columnIndex - (rows - 1 - rowIndex), rows - 1)
            else -> listOf(0, columnIndex + rowIndex)
        }
        var i = leftDiagonalCoords[0]
        for (j in leftDiagonalCoords[1] downTo 0) {
            if (i > structure.lastIndex) break
            counter++

            if (structure[i].lastIndex < j || structure[i][j] != disc) {
                counter = 0
            }

            if (counter >= 4) return true

            i++
        }

        return false
    }

    fun draw() {
        // Draw numbered head
        for (i in 1..columns) print(" $i"); println()

        // Draw rows
        for (rowIndex in rows - 1 downTo 0) {
            for (column in structure) {
                print("║")
                if (rowIndex in column.indices) print(column[rowIndex]) else print(" ")
            }
            println("║")
        }

        // Draw footer
        print("╚")
        repeat(columns - 1) {
            print("═╩")
        }
        println("═╝")
    }
}