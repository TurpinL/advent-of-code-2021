val inputLines = input().lines()

val xExtent = inputLines.size
val yExtent = inputLines.first().length

xExtent
yExtent

val octopuses = Array(xExtent) { x ->
    Array(yExtent) { y ->
        inputLines[x][y].toString().toInt()
    }
}

var flashes = 0

// Print Map
println("Before any steps")
printOctopuses()

var i = 1
while (true) {
    // Step 1, increment
    for (x in 0 until xExtent) {
        for (y in 0 until yExtent) {
            octopuses[x][y]++
        }
    }

    // Step 2, flash
    val octopusesToCheck = (0 until xExtent).map { x ->
        (0 until yExtent).map { y ->
            Pair(x, y)
        }
    }.flatten().toMutableList()

    while(octopusesToCheck.isNotEmpty()) {
        val (x, y) = octopusesToCheck.removeAt(0)


        if (octopuses[x][y] > 9) {
            // Flash
            flashes++
            octopuses[x][y] = 0

            // Charge Neighbours
            listOf(
                Pair(x + 1, y),
                Pair(x - 1, y),
                Pair(x, y + 1),
                Pair(x, y - 1),
                Pair(x + 1, y + 1),
                Pair(x + 1, y - 1),
                Pair(x - 1, y + 1),
                Pair(x - 1, y - 1),
            ).filter { (neighbourX, neighbourY) ->
                neighbourX in 0 until xExtent &&
                neighbourY in 0 until yExtent &&
                octopuses[neighbourX][neighbourY] != 0
            }.forEach { (neighbourX, neighbourY) ->
                octopuses[neighbourX][neighbourY]++
                // This causes rechecking of a lot of octopuses...
                octopusesToCheck.add(Pair(neighbourX, neighbourY))
            }
        }
    }

    // Answers
    if (i == 100) {
        println("Part 1: Flash count after 100 steps: $flashes")
    }

    //printOctopuses()

    if (octopuses.flatten().sum() == 0) {
        println("Part 2: Simultaneous flash after step $i!")
        break
    }

    i++
}

fun printOctopuses() {
    for (x in 0 until xExtent) {
        for (y in 0 until yExtent) {
            print(octopuses[x][y])
            print(" ")
        }
        println()
    }
}

fun input(): String {
    return """
4721224663
6875415276
2742448428
4878231556
5684643743
3553681866
4788183625
4255856532
1415818775
2326886125
""".trimIndent()
}