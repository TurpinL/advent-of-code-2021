val isTest = false

val caves = constructCaveSystem(input().lines())

printCaves(caves)

val incompletePaths = mutableListOf(
    Path(listOf(caves["start"]!!), false)
)
val completePaths = mutableListOf<Path>()

crunchNumbers()

println("incomplete")
println("incomplete")
println("incomplete")
println("incomplete")


crunchNumbers()

println("incomplete")
//incompletePaths.forEach {println(it)}
//println(incompletePaths.count())
//println("complete")
//completePaths.forEach {println(it)}
println(completePaths.count())

data class Path(
    val path: List<Cave>,
    val hasVisitedASmallCaveTwice: Boolean
) {

}

data class Cave(
    val id: String,
    val isSmall: Boolean,
    val connections: MutableList<Cave> = mutableListOf()
) {
    override fun toString():String {
        return id
    }

    fun toVerboseString():String {
        return "$id ${if (isSmall) "small" else "big"} " +
                "-> ${connections.map { it.id }}"
    }
}

fun constructCaveSystem(connectionStrings: List<String>): Map<String, Cave> {
    val caves = mutableMapOf<String, Cave>()

    for (line in connectionStrings) {
        val connection = line.split("-")

        // Add new caves to the cave system
        connection.forEach { caveId ->
            if (!caves.containsKey(caveId)) {
                val isSmall = caveId.all { it.isLowerCase() }
                caves[caveId] = Cave(caveId, isSmall)
            }
        }

        // Add the connection to those caves
        val firstCave = caves[connection.first()]
        val secondCave = caves[connection.last()]

        if (firstCave != null && secondCave != null) {
            firstCave.connections.add(secondCave)
            secondCave.connections.add(firstCave)
        }
    }

    return caves
}

fun printCaves(caves: Map<String, Cave>) {
    println("printCaves:")
    caves.forEach {
        println(it.value)
    }
}

fun input(): String {
    return if (isTest) {
        """
fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW
        """.trimIndent()
    } else {
        """
            QF-bw
            end-ne
            po-ju
            QF-lo
            po-start
            XL-ne
            bw-US
            ne-lo
            nu-ne
            bw-po
            QF-ne
            ne-ju
            start-lo
            lo-XL
            QF-ju
            end-ju
            XL-end
            bw-ju
            nu-start
            lo-nu
            nu-XL
            xb-XL
            XL-po
        """.trimIndent()
    }
}

fun crunchNumbers() {
    var count = 0

    while (incompletePaths.isNotEmpty()) {
        val currentPath = incompletePaths.removeAt(0)

        // Expand current path
        val possibleNextCaves = currentPath.path.last().connections
            .filter { nextCave ->
                if (nextCave.id == "start") {
                    false
                } else {
                    if (nextCave.isSmall) {
                        val hasVisitedThisBefore = nextCave in currentPath.path
                        if (currentPath.hasVisitedASmallCaveTwice) {
                            !hasVisitedThisBefore
                        } else {
                            true
                        }
                    } else {
                        true
                    }
                }
            }

        if (possibleNextCaves.isEmpty()) {
            incompletePaths.remove(currentPath)
        }

        possibleNextCaves.forEach {
            val isRevisitingASmallCave = it.isSmall && it in currentPath.path
            val extendedPath = Path(
                path = currentPath.path + listOf(it),
                hasVisitedASmallCaveTwice = currentPath.hasVisitedASmallCaveTwice || isRevisitingASmallCave
            )

            if (it.id == "end") {
                completePaths.add(extendedPath)
            } else {
                incompletePaths.add(extendedPath)
            }
        }

        count++
        if (count % 5000 == 0) {
            println("incomplete ${incompletePaths.count()}\tcomplete ${completePaths.count()}")
        }

        if (count > 100000) break
    }
}