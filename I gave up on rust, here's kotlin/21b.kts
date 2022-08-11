// Roll - Combinations
// 3 - 1 (111)
// 4 - 3 (112, 121, 211)
// 5 - 6 (113, 131, 311, 122, 212, 221)
// 6 - 7 (213, 231, 123, 321, 132, 312, 222)
// 7 - 6 (223, 232, 322, 133, 313, 331)
// 8 - 3 (332, 323, 233)
// 9 - 1 (333)

// Shortest game
// 3 turns, 987
// Longest game
// 9 turns?

// Assumption: We can pretend the players turns aren't interspersed.
// Player 1 can take all it's turns then player 2 takes all their turns.
// This shouldn't affect probability

// For each player, If I figure out how in how many universes exist
// where player 1 gets a certain amount of points on a certain turn
// and the same for player 2, I think I can corroborate that information
// to get the answer
//
// For each turn where Player 1 can win, multiply the # of universes where that
// happens by the # of universes where Player 2 gets less than 21 points on the
// previous turn. We check the previous turn because player 1 is always 1 turn ahead
//
// Then we check the same in the other direction. Player 2's wins * players 1's losses.
// But player 1 will have played the current turn too, so we check both player's score
// for the same turn

// We only count the universes where someone actually wins, not the ones created on the way


data class PossibleTurn(
    val roll: Int,
    val points: Int,
    var nextTurns: MutableList<PossibleTurn>
)