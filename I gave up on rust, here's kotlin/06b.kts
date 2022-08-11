import java.math.BigInteger

var fishies = input().split(",").map { it.toInt() }.toMutableList()

var fishBuckets = Array(9) { 0.toULong() }

fishies.forEach {
    fishBuckets[it] += 1.toULong()
}


for (x in 1..256) {
    printBuckets(fishBuckets)

    val newFish = fishBuckets[0]

    for (i in 0..7) {
        fishBuckets[i] = fishBuckets[i + 1]
    }

    fishBuckets[6] += newFish
    fishBuckets[8] = newFish
}

printBuckets(fishBuckets)

fishBuckets.sum()

fun printBuckets(buckets: Array<ULong>) {
    for (x in buckets) {
        print("$x, ")
    }
    println()
}

fun input(): String {
    return """4,1,1,1,5,1,3,1,5,3,4,3,3,1,3,3,1,5,3,2,4,4,3,4,1,4,2,2,1,3,5,1,1,3,2,5,1,1,4,2,5,4,3,2,5,3,3,4,5,4,3,5,4,2,5,5,2,2,2,3,5,5,4,2,1,1,5,1,4,3,2,2,1,2,1,5,3,3,3,5,1,5,4,2,2,2,1,4,2,5,2,3,3,2,3,4,4,1,4,4,3,1,1,1,1,1,4,4,5,4,2,5,1,5,4,4,5,2,3,5,4,1,4,5,2,1,1,2,5,4,5,5,1,1,1,1,1,4,5,3,1,3,4,3,3,1,5,4,2,1,4,4,4,1,1,3,1,3,5,3,1,4,5,3,5,1,1,2,2,4,4,1,4,1,3,1,1,3,1,3,3,5,4,2,1,1,2,1,2,3,3,5,4,1,1,2,1,2,5,3,1,5,4,3,1,5,2,3,4,4,3,1,1,1,2,1,1,2,1,5,4,2,2,1,4,3,1,1,1,1,3,1,5,2,4,1,3,2,3,4,3,4,2,1,2,1,2,4,2,1,5,2,2,5,5,1,1,2,3,1,1,1,3,5,1,3,5,1,3,3,2,4,5,5,3,1,4,1,5,2,4,5,5,5,2,4,2,2,5,2,4,1,3,2,1,1,4,4,1,5""".trimIndent()
}