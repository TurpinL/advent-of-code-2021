val isTest = true

val caves = constructCaveSystem(input().lines())

printCaves(caves)

val incompletePaths = mutableListOf(listOf(caves["start"]!!))
val completePaths = mutableListOf<List<Cave>>()

while (incompletePaths.isNotEmpty()) {
    val currentPath = incompletePaths.removeAt(0)

    // Expand current path
    currentPath.last().connections
        .filter { !it.isSmall || it !in currentPath }
        .forEach {
            if (it.id == "end") {
                completePaths.add(currentPath + listOf(it))
            } else {
                incompletePaths.add(currentPath + listOf(it))
            }
        }
}

//println(completePaths)
println(completePaths.count())

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