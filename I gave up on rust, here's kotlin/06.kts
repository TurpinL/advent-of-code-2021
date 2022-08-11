var fishies = input().split(",").map { it.toInt() }.toMutableList()

for (x in 1..80) {
    var newFish = 0

    fishies = fishies.map {
        if (it == 0) {
            newFish++
            6
        } else {
            it - 1
        }
    }.toMutableList()

    for (i in 1..newFish) {
        fishies.add(8)
    }
}

println(fishies.count())

fun input(): String {
    return """3,4,3,1,2""".trimIndent()
}