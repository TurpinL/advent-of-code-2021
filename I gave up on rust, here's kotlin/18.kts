import kotlin.math.ceil
import kotlin.math.floor

val isTest = true

val inputLines = input().lines()

val example = "[[[[[9,8],1],2],3],4]"

val snailFishNum = parseSnailFishNum(example)

fun explode(num: SnailPair, depth: Int = 0): SnailFishNum? {
    if (depth == 4) {
        return num
    } else {
        if (num.left is SnailPair) {
            explode(num.left, depth+1)
        }
    }
}

sealed class SnailFishNum {
    operator fun plus(other: SnailFishNum): SnailFishNum {
        return SnailPair(this, other)
    }

//    fun reduced(): SnailFishNum {
//
//    }
}

class RegularNum(val value: Int) : SnailFishNum() {
    override fun toString(): String {
        return "$value"
    }

    fun exploded(): SnailPair {
        return SnailPair(
            RegularNum(floor(value / 2.0).toInt()),
            RegularNum(ceil(value / 2.0).toInt()),
        )

    }
}

class SnailPair(val left: SnailFishNum, val right: SnailFishNum) : SnailFishNum() {
    override fun toString(): String {
        return "($left, $right)"
    }
}

fun parseSnailFishNum(unparsedNum: String): SnailFishNum {
    var depth = 0
    var middleIndex = -1

    if (',' !in unparsedNum) {
        // It must be a number!
        return RegularNum(unparsedNum.toInt())
    }

    // Find the comma for this depth
    for ((i, char) in unparsedNum.withIndex()) {
        when (char) {
            '[' -> depth++
            ']' -> depth--
            ',' -> {
                if (depth == 1) {
                    middleIndex = i
                    break
                }
            }
        }
    }

    return SnailPair(
        parseSnailFishNum(unparsedNum.substring(1, middleIndex)),
        parseSnailFishNum(unparsedNum.substring(middleIndex + 1, unparsedNum.lastIndex))
    )
}

fun input(): String {
    return if (isTest) {
        """""".trimIndent()
    } else {
        """""".trimIndent()
    }
}