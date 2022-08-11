import kotlin.math.ceil
import kotlin.math.floor

val isTest = false

val inputNumbers = input().lines().map { parseSnailFishNumber(it) }

val sum = inputNumbers.reduce { acc, depthNums ->
    acc add depthNums
}

println("Part 1: ${magnitude(sum)}")

println("Part 2:")
val numberPairs = inputNumbers.flatMap {
    inputNumbers
        .filter { other -> other != it }
        .map { other -> Pair(it, other) }
}

numberPairs.maxOf {
    magnitude(it.first add it.second)
}

fun magnitude(number: List<DepthNum>): Int {
    val mutNum = number.toMutableList()

    while (mutNum.count() > 1) {
        val nextPairIndex = mutNum.windowed(2).indexOfFirst {
            it.first().depth == it.last().depth
        }

        // Replace that pair with it's magnitude
        val left = mutNum.removeAt(nextPairIndex)
        val right = mutNum.removeAt(nextPairIndex)
        mutNum.add(
            nextPairIndex,
            DepthNum(
                value = left.value*3 + right.value*2,
                depth = left.depth - 1
            )
        )
    }

    return mutNum.first().value
}

infix fun List<DepthNum>.add(other: List<DepthNum>): List<DepthNum> {
    val sum = (this + other).map {
        it.copy(depth = it.depth + 1)
    }.toMutableList()

    reduce(sum)
    return sum
}

fun reduce(number: MutableList<DepthNum>) {
    var didSplit: Boolean

    do {
        while (explode(number)) {}
        didSplit = split(number)
    } while (didSplit)
}

fun split(number: MutableList<DepthNum>): Boolean {
    val splitIndex = number.indexOfFirst { it.value >= 10 }

    if (splitIndex == -1) {
        return false
    } else {
        val numToSplit = number[splitIndex].value
        val left = floor(numToSplit / 2.0).toInt()
        val right = ceil(numToSplit / 2.0).toInt()

        val newDepth = number[splitIndex].depth + 1
        number.removeAt(splitIndex)
        number.add(splitIndex, DepthNum(value = right, depth = newDepth))
        number.add(splitIndex, DepthNum(value = left, depth = newDepth))

        return true
    }
}

fun explode(number: MutableList<DepthNum>): Boolean {
    val explodeAt = number.indexOfFirst { it.depth == 5 }

    if (explodeAt == -1) {
        return false
    } else {
        if (number[explodeAt+1].depth != 5) {
            println("Assumption about depth is wrong")
            error("Assumption about depth is wrong")
        }

        val leftExplodee = number[explodeAt]
        val rightExplodee = number[explodeAt + 1]

        // Add the first of the pair to the previous number, if it exists
        val prevNumIndex = explodeAt - 1
        if (prevNumIndex >= 0) {
            val prevNum = number[prevNumIndex]
            number[prevNumIndex] = prevNum.copy(value = prevNum.value + leftExplodee.value)
        }
        // Add the second of the pair to the next number, if it exists
        val nextNumIndex = explodeAt + 2
        if (nextNumIndex <= number.lastIndex) {
            val nextNum = number[nextNumIndex]
            number[nextNumIndex] = nextNum.copy(value = nextNum.value + rightExplodee.value)
        }
        // Replace the pair with a single 0 at one less depth
        number.removeAt(explodeAt)
        number[explodeAt] = DepthNum(value = 0, depth = 4)

        return true
    }
}

fun parseSnailFishNumber(unparsedNumber: String): List<DepthNum> {
    var depth = 0
    val snailFishNumber: MutableList<DepthNum> = mutableListOf()

    for (char in unparsedNumber) {
        when (char) {
            '[' -> depth++
            ']' -> depth--
            ',' -> {}
            else -> snailFishNumber.add(DepthNum(value = char.toString().toInt(), depth = depth))
        }
    }

    return snailFishNumber
}

data class Vec2(
    val x: Double,
    val y: Double
)

data class DepthNum(
    val value: Int,
    val depth: Int
) {
    override fun toString(): String {
        return "${value}d$depth"
    }
}

fun input(): String {
    return if (isTest) {
        """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent()
    } else {
        """
            [[2,[[1,1],9]],[[0,[3,0]],[[1,6],[4,2]]]]
            [[8,[0,5]],[[[9,9],0],[[2,9],2]]]
            [[[[6,4],[5,8]],[[0,9],[6,5]]],[[5,1],4]]
            [[[9,[2,8]],[[0,2],[8,3]]],[[[5,6],[5,8]],[[4,8],2]]]
            [[0,[[0,1],[6,0]]],[[[6,4],1],[8,6]]]
            [[[[8,5],6],8],[[[9,1],[0,6]],[4,[2,4]]]]
            [7,[[4,3],[8,5]]]
            [[8,[1,[3,4]]],[[3,8],[0,1]]]
            [[[1,1],[[2,1],[0,3]]],[[7,[1,8]],[[3,8],[5,2]]]]
            [[2,[[4,6],[6,2]]],[[0,5],[3,7]]]
            [[[[9,8],[4,6]],[7,[9,1]]],[[[8,7],[4,7]],[[6,6],[8,1]]]]
            [[[2,[5,1]],[[0,4],3]],[[9,7],[[0,2],0]]]
            [[[[5,0],2],5],[[3,[5,8]],[5,[8,9]]]]
            [[6,[3,6]],[[[2,7],6],[[6,0],4]]]
            [[8,8],7]
            [[[[7,9],3],8],[[0,[1,7]],[[3,2],[4,5]]]]
            [[[1,1],[7,2]],[3,[4,[6,4]]]]
            [[[9,[6,6]],[[4,8],[1,3]]],[[[4,7],8],[[5,2],[3,8]]]]
            [[[6,[6,7]],[3,4]],5]
            [[[[0,0],2],9],[[[2,1],1],[5,[4,7]]]]
            [[[2,[9,8]],[5,8]],[[[3,4],6],[5,0]]]
            [[[7,[9,4]],[7,[7,2]]],[[1,[9,6]],1]]
            [[[[9,1],1],[4,[2,6]]],3]
            [[0,[8,[3,4]]],[8,[9,8]]]
            [[[1,6],[6,7]],[[[0,4],1],7]]
            [[6,[5,[0,0]]],[7,[[5,4],1]]]
            [[2,[[9,5],[9,1]]],[[3,0],4]]
            [[[5,7],[[1,0],[3,5]]],[4,[5,[4,0]]]]
            [[3,3],[2,2]]
            [[[[6,2],[1,7]],[1,7]],[[[6,7],6],9]]
            [[[[9,8],[8,8]],[2,1]],[[8,4],8]]
            [[[[1,4],1],[2,0]],[4,[[0,5],5]]]
            [[[7,[6,0]],[[7,3],1]],9]
            [[[[2,4],0],[[6,9],8]],[[3,[0,9]],[[4,4],[5,4]]]]
            [[7,3],[0,[2,[7,2]]]]
            [[[[8,8],5],9],[[8,6],6]]
            [[[[9,5],7],9],0]
            [[[1,4],8],[[7,[5,3]],[[6,4],6]]]
            [[9,[[9,3],[3,7]]],[[[6,9],1],[[2,3],[4,4]]]]
            [[4,[9,2]],[3,4]]
            [[1,[[0,9],2]],[1,[1,[8,7]]]]
            [[[4,1],8],[9,[9,[2,9]]]]
            [[[[7,9],[9,7]],8],[[[3,0],5],[[7,8],[3,1]]]]
            [[[[9,4],[9,9]],[[9,5],[8,9]]],[[2,[7,4]],[[4,6],6]]]
            [[[[8,7],1],[6,8]],[[4,2],5]]
            [7,[3,[3,3]]]
            [[[4,9],[0,2]],[[[4,2],9],[[5,8],6]]]
            [[[[1,3],1],[[7,5],[4,0]]],[[[6,3],4],[[1,2],8]]]
            [[[[3,2],2],[4,7]],[[[5,6],[6,3]],3]]
            [[[[4,0],6],[4,2]],[7,5]]
            [[[[9,5],[2,0]],[[6,8],[0,9]]],[[[7,4],[3,6]],1]]
            [[[4,[9,3]],[[9,4],8]],[[6,[1,2]],2]]
            [[[[4,1],[1,1]],[[4,8],9]],[[[1,0],[0,3]],2]]
            [[[3,[3,8]],[[0,6],7]],[[2,5],9]]
            [[[0,[6,8]],[[2,7],[4,1]]],6]
            [[6,3],0]
            [[[3,[7,1]],[3,[2,0]]],[[[3,5],9],[[5,2],[7,8]]]]
            [[7,8],[1,[[7,1],5]]]
            [[[9,[8,9]],2],[9,[[8,8],4]]]
            [[[8,[5,8]],[[9,1],[6,0]]],[[[9,1],[4,7]],8]]
            [5,[[[4,9],7],[[6,0],[9,0]]]]
            [[[[8,8],[6,7]],[[1,0],6]],[[5,[2,8]],[[8,0],[3,7]]]]
            [[0,[6,6]],[[0,1],[3,[9,2]]]]
            [[1,[0,[8,1]]],[[0,[0,0]],[8,[0,0]]]]
            [[[4,[1,4]],[8,[9,5]]],7]
            [7,[[[0,0],[4,3]],8]]
            [[[9,1],[[7,5],[9,2]]],[5,[9,0]]]
            [[[[2,0],9],[8,[3,0]]],[[9,8],[4,[0,7]]]]
            [4,[5,[5,[0,3]]]]
            [[6,[[6,9],8]],[1,[0,[6,0]]]]
            [[7,[4,3]],[[0,6],[[5,2],[6,9]]]]
            [[[[7,2],[4,6]],[[5,0],9]],6]
            [[[0,1],[0,2]],[0,[5,2]]]
            [[[[5,0],[5,4]],[[5,9],[9,9]]],[2,[[3,0],[8,1]]]]
            [[[[9,2],[2,9]],[[5,5],2]],[[1,3],[[3,6],[1,8]]]]
            [[0,[2,4]],[[[6,9],1],[[7,9],[9,8]]]]
            [[[[2,1],1],[7,3]],[4,[[1,2],[2,6]]]]
            [[[6,[0,1]],[[6,4],[4,2]]],[1,[[0,0],[9,7]]]]
            [[[[9,2],3],[9,8]],[[6,5],[7,[1,7]]]]
            [[3,9],7]
            [[[6,9],[[0,2],0]],[[[8,6],2],9]]
            [[[[2,2],2],[[6,7],7]],[[0,3],9]]
            [[[7,[2,7]],3],4]
            [[[[1,9],6],[0,7]],[[[2,2],1],2]]
            [9,9]
            [0,[9,[[4,1],1]]]
            [[[[7,6],1],2],[[[6,9],[9,1]],0]]
            [[[[4,3],[4,2]],3],[[5,[6,5]],[[2,6],0]]]
            [[[0,[5,1]],[6,[1,4]]],[5,[[8,1],3]]]
            [6,[9,6]]
            [[8,[9,[6,8]]],[[4,9],[[2,4],[7,1]]]]
            [[5,[[9,9],[3,3]]],[[[9,8],[5,0]],6]]
            [[6,7],1]
            [1,[4,[[9,6],0]]]
            [[[[9,8],[7,8]],[5,[4,6]]],[[[5,9],6],[[4,6],4]]]
            [[[2,7],4],[[[0,3],0],[[7,4],[7,4]]]]
            [7,[0,4]]
            [1,[3,2]]
            [[3,0],8]
            [[[3,2],5],8]
        """.trimIndent()
    }
}