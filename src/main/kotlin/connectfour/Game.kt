package connectfour

class Game(firstPlayer: String, secondPlayer: String) {
    private val gameOverInput = "end"
    private val player1: Player = Player(firstPlayer, 1)
    private val player2: Player = Player(secondPlayer, 2)
    private var board: Board? = null
    private var rounds: Int = 0

    fun start() {
        // Ask for a number of games
        initGamesNumber()

        // Output initial settings
        println("${player1.name} VS ${player2.name}")
        println("${board!!.rows} X ${board!!.columns} board")
        if (rounds > 1) println("Total $rounds games")

        // Main game loop
        for (round in 1..rounds) {
            if (rounds > 1) println("Game #$round") else println("Single game")

            if (!play(round)) return

            if (rounds > 1) println(
                "Score\n"
                        + "${player1.name}: ${player1.score} ${player2.name}: ${player2.score}"
            )

            board!!.clear()
        }
    }

    fun play(round: Int): Boolean {
        // Output board
        board!!.draw()

        var turn = true

        while (true) {
            // Set up vars
            val player = if (round % 2 != 0) {
                if (turn) player1 else player2
            } else {
                if (turn) player2 else player1
            }

            // Ask for turn
            println("${player.name}'s turn:")
            val input = readln()

            // Check game over
            if (input == gameOverInput) return false

            // Check not integer
            if (!Regex("\\d+").matches(input)) {
                println("Incorrect column number")
                continue
            }

            val columnNumber = input.toInt()

            if (board!!.isColumnOutOfRange(columnNumber)) continue
            if (board!!.isColumnFull(columnNumber)) continue

            // Add disk to column
            board!!.addDisk(player, columnNumber)

            // Output board
            board!!.draw()

            // Check draw
            if (board!!.isFull()) {
                player1.addDrawScore(); player2.addDrawScore()
                println("It is a draw")
                break
            }

            // Check winning condition
            if (board!!.isPlayerWon(player, columnNumber - 1)) {
                player.addWinScore()
                println("Player ${player.name} won")
                break
            }

            // Switch turn
            turn = !turn
        }

        return true
    }

    fun initBoard() {
        var rows: Int
        var columns: Int
        while (true) {
            println(
                """Set the board dimensions (Rows x Columns)
                |Press Enter for default (6 x 7)
                """.trimMargin()
            )

            val size = readln().trim().uppercase()

            // Check if empty - then use default
            if (size.isEmpty()) {
                rows = 6; columns = 7; break
            }

            // Check if format is correct
            if (!Regex("\\d+\\s*X\\s*\\d+").matches(size)) {
                println("Invalid input"); continue
            }

            rows = size.first().toString().toInt()
            columns = size.last().toString().toInt()

            // Check ranges
            if (rows !in MIN_ROW_COLUMN_NUMBER..MAX_ROW_COLUMN_NUMBER) {
                println("Board rows should be from $MIN_ROW_COLUMN_NUMBER to $MAX_ROW_COLUMN_NUMBER"); continue
            }
            if (columns !in MIN_ROW_COLUMN_NUMBER..MAX_ROW_COLUMN_NUMBER) {
                println("Board columns should be from $MIN_ROW_COLUMN_NUMBER to $MAX_ROW_COLUMN_NUMBER"); continue
            }

            break
        }

        board = Board(rows, columns)
    }

    private fun initGamesNumber() {
        while (true) {
            println(
                """Do you want to play single or multiple games?
                |For a single game, input 1 or press Enter
                |Input a number of games:
                """.trimMargin()
            )

            val rounds = readln().trim()

            // Check if format is correct
            if (!Regex("[1-9]*").matches(rounds)) {
                println("Invalid input"); continue
            }

            if (rounds.isEmpty() || rounds.toInt() == 1) {
                this.rounds = 1; break
            }

            this.rounds = rounds.toInt()

            break
        }
    }
}