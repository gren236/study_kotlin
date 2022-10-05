package connectfour

class Player(val name: String, order: Int) {
    val disk: Char = when (order) {
        1 -> 'o'
        2 -> '*'
        else -> ' '
    }
    var score: Int = 0

    fun addWinScore() {
        score += 2
    }

    fun addDrawScore() {
        score++
    }
}