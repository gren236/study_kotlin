package connectfour

const val MIN_ROW_COLUMN_NUMBER = 5
const val MAX_ROW_COLUMN_NUMBER = 9

fun main() {
    // Ask names
    println("Connect Four")
    println("First player's name:")
    val firstPlayer = readln().trim()
    println("Second player's name:")
    val secondPlayer = readln().trim()

    val game = Game(firstPlayer, secondPlayer)

    game.initBoard()
    game.start()

    println("Game Over!")
}