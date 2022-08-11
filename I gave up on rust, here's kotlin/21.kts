val board = (1..10).toList()

var lastDiceRoll = 0
var p1Pos = 6 - 1
var p1Score = 0
var p2Pos = 7 - 1
var p2Score = 0

var isP1sTurn = true
var turns = 0


while (p1Score < 1000 && p2Score < 1000) {
    if (isP1sTurn) {
        val roll = rollForTurn()

        p1Pos = (p1Pos + roll) % board.count()
        p1Score += board[p1Pos]

        isP1sTurn = false
    } else {
        val roll = rollForTurn()

        p2Pos = (p2Pos + roll) % board.count()
        p2Score += board[p2Pos]

        isP1sTurn = true
    }

    turns++
}

p1Score
p2Score
turns * 3

fun rollForTurn(): Int {
    return rollDice()+rollDice()+rollDice()
}

fun rollDice(): Int {
    if (lastDiceRoll == 100) {
        lastDiceRoll = 1
    } else {
        lastDiceRoll++
    }

    return lastDiceRoll
}