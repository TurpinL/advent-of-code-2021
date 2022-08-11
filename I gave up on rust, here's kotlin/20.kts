val isTest = false

val inputLines = input().lines()
val enhancementAlgorithm = inputLines.first()
val rawInitialImage = inputLines.drop(2) // Drop the algorithm and blank line

enhancementAlgorithm
rawInitialImage

var xMin = 0
var xMax = rawInitialImage.first().lastIndex
var yMin = 0
var yMax = rawInitialImage.lastIndex
var lightPixels = mutableSetOf<Coord>()



rawInitialImage.forEachIndexed { y, row ->
    row.forEachIndexed { x, pixel ->
        if (pixel == '#') {
            lightPixels.add(Coord(x, y))
        }
    }
}

printImage()

for (i in 1 .. 50) {

    val tempLightPixels = mutableSetOf<Coord>()
    for (y in (yMin-1)..(yMax+1)) {
        for (x in (xMin-1)..(xMax+1)) {
            if (getEnhancedPixel(Coord(x, y), lightPixels, enhancementAlgorithm, i)) {
                tempLightPixels.add(Coord(x, y))
            }
        }
    }

    xMin--
    yMin--
    xMax++
    yMax++
    lightPixels = tempLightPixels

    println("after interation $i. Light pixel count = ${lightPixels.count()}")
}

printImage()

data class Coord(
    val x: Int,
    val y: Int
)

fun printImage() {
    for (y in yMin..yMax) {
        for (x in xMin..xMax) {
            if (Coord(x, y) in lightPixels) {
                print("█")
            } else {
                print(" ")
            }
        }
        println()
    }
}

fun getEnhancedPixel(
    target: Coord,
    lightPixels: Set<Coord>,
    algorithm: String,
    stepNum: Int
): Boolean {
    val window = listOf(
        // y is up. You're in a submarine
        Coord(target.x-1, target.y-1),
        Coord(target.x, target.y-1),
        Coord(target.x+1, target.y-1),

        Coord(target.x-1, target.y),
        Coord(target.x, target.y),
        Coord(target.x+1, target.y),

        Coord(target.x-1, target.y+1),
        Coord(target.x, target.y+1),
        Coord(target.x+1, target.y+1),
    )

    val algorithmIndex = window
        .map {
            if (it.x !in xMin..xMax || it.y !in yMin..yMax) {
                if (stepNum % 2 == 0) "1" else "0"
            } else {
                if (it in lightPixels) "1" else "0"
            }
        }
        .joinToString("")
        .toInt(2)

    return algorithm[algorithmIndex] == '#'
}

fun input(): String {
    return if (isTest) {
        """
            ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
            
            #..#.
            #....
            ##..#
            ..#..
            ..###
        """.trimIndent()
    } else {
        """
            ###..##....#....##...#.#.#...######.#.#.#.###.####.....#.##...###......###.......#.#.#...##.##...#.#####..#.#.#.###..####..#.#..#..#...###.####..#.###.#...#.#.#.#...#.....#####..#.##.#.#.#.#...####.#.#....##..######..###.####......#..#..#####.##.#.#.#.#.####...###.####..#.#.#..##...#..#..#..##..##..###..#..#####.##...#.#.....##.#..##.#...####.#...#..##.#.#....#..#....##.#.#.###.#..#.###.###.######.##.######...#..##..##.#.###..#.###..#..#.########.###.#.#.#.#..##.#.#.###.#.###.#.#.###...#..#.##....##.##.##..

            .....#...#.####...#####..##..#.##.######...###.##.#.#.#...#####.#.#........#.######.#.#.##..####..#.
            ##.#...##...######...##..####..####..#...##.#..##.#.##.#.....##.##.#.##...#####..#..#....#.###...###
            ..#.##...#..#.....#..##.##...###..##..##....##...#..####.#....#.#.##.#.#.#..#.#.##..##.##.###.##..##
            ......##.##.#.#.#....#..##.###.##.#.###..###.###....#...##.####.#.####.###..#..##.#.###....#.#..#.#.
            #.#...##.######.#..###..#.....##....####.#..###.##.#...#.#.#.#..####..#.#....#.#........#.#..#...##.
            .#.#####..##..###########.#..##.#..#####.#.#.###..#..##.##.#.#...#..##.#####...#.......#....#....###
            ..#..#.##.#..##...##.#.#...###.#...#..##.##..####.#.#........##.#....#..###...##.#.#####.#...#..#.#.
            .###...###.#..#.##.#.##..#.#####.#.#...#.###.#...###.#...#....##.#..#.#####..##.#.###.#.....#..#.#..
            ###...##..###.##...#..##...##.##..##.#..#.####.#.#.##..##.#..#.......##.#....####.#.####..###..#...#
            .#.....#.#.####..#.##.##..#######.##.###.#...#..#.##..#....#..####.#.#....#.#..#.#....##..#####..#.#
            .#.#..#..##..#..##.#........#...###..###.#...#.#...##..###...#.####..#####.####..#..##.##.####...###
            ##.#....##.#.#..#.##.##.#.#...#.##.#.#....##.#.##.#.#####..#.#....#.###..#.#....###......####.######
            ##..###.#.#.##.###...#.#.......#.#.##..##..####...#.###.##.##.#.#..###.##..##.#......##.###.....##..
            .#########..##..##.##.#.##..#.#######.#.#..######..###...##....####.#......###.####.#.#..####.###..#
            ....###.####..#.#.....#...#......##.##.###.#.#.###..#.###..#..###....#.##.#.##.#.#..###..#..##..#.##
            #..#.#..#.#..##..##.####....##..#..#.####.#.#.#..##.##...#.###.##...###.#####..####.####..#..#####.#
            ..#.......##....#.##...#.##...####..##...#.##..#.....#.######..##.#.#..##.##..##..#...#####..#...#..
            ....#.#.###.##..#..###..#.#..#.###.##....#..#.##.##..##.#.####..####....##..####.#.##.#.#.######..##
            #.##.####.#####..####..##..###.########.#.#.#...##..#..#.#..#..#..###.##...##....#.#.##.#####....##.
            #.#..#.#.#..#.#.#.....#..#..#.####.##########.###.#....##.#.###.#.##.....#....#..##.###.###.#...###.
            .##.###.######.....#####.......###.#######.#....#.##..##...#...###.##.##.#..####....##.#..#.##.#.#.#
            .####..#.##.#.#...#.....###.....##..###.#.#.#..####..##..#.#######.#.#.##.######.##...###...#.#..#..
            .##.#..#.#.##..#..##.#..#...#...##...###...#.#.####.#...###...#.#.....####......#.######..####.#.###
            ##..#.###..###....##.####.###..#..#.##.####.#.#.##..###..#.#.#..#..######...####...#..##.##..##..#.#
            ##.#...#######.####.#.##.##.#.##...#.#...#.##.####.#.##.#..#.###......##.#.#.#...###.#...##..##.###.
            ..###.#.##.#.#....##.#.###....##.#....##.##.#...##.#.#...###..#.#........#####..#####...##..#.#...#.
            ##.....#...#.#...#....####..#.####.#.#......##....#...####..#.##..####..#.#.####.##.##.#.##.#####.#.
            ....######....#..###.###..#..#####.###....#.##...#...#.#..#.....#.#####.##...##..###.##.#..#.####.##
            .##..###.#.#.#####...###.##.###.#.##..##..#..#.##.##....##..##...##.####..#.####...##.##.##...#..#..
            #.#..#####.#.#####...#.#..#...#.#.###...##.#.######....##....##.#..#.##.##.###..##.##.#####.#...##.#
            #..###.###.#.#####.#..#......#.#.##.###..##.#...###..####.#..#####...##.####.#...##.#.###.##...###.#
            ###.###..####.#.#....#...#..##...#.###..#..#####.###.#.#.#..#..#.##...###.#.#.####.#.###..##..##.##.
            .#.#..#.#.#..#....##.#....#...#..#.#....###.#.#......##..##..#..###......##.####.#########....##...#
            #.....#.....##..##..###.##.##..####....#..#.##.##.#..##.##.#..####..#....####.#.#...##.#.#.###.#.##.
            .#..##.###.##..#..##..#.#..####......#..###.##..##...##..#....###.#.#.....###.##.##..##....#.#.##...
            .#...##.....#####.###..#...#.#.#...........#.##.#.......#.####...###.##..#..##.#...#..#########...#.
            ########.####.##.......#.##.###.##.#.####.#..##.##.#.###..##.####..#..##.#..#...#.###......##.##.###
            .#.##...#.#######.###.#.######.#..###.#..##.#..##.###..##.#.##....####...#..##..#....#.#.##.#.##.#.#
            ...#.#.#..##..##.#..####....##....#..#.#.#.##.#..#.#..#...####......##.....#.#.#..##...#..#.#..#...#
            .#.#####.#.#.#####.#####....##..##....####..#.###.#...###.#.##.#.#..##.#......#.###..#####....#..##.
            .#.###..####..###.###...#...#.###.#.##.##.##...#.....##.##..##..######.#.##.#.######..#.#.#.......#.
            #.....##.#..#.....#.##.##..#.#.###..##.##.##.#..#.#...#.#...##.....#.##.#.####.#.##.##.#.##.#..#.#..
            .#.#...#.##.##.#.#...#......##.#....#####.###.###.######..#.##.#..#.#..####..####.###..#.#....#..#.#
            .##.#..####.#...####..#..##.##.###..#..######..##.####....#..#.##..###..##.##.......#.##..##..###..#
            #....##.#.#.#...#.#..#...##.#.........##.#######..####.##..##.#####.##.#....##..#.####..###.####.#..
            #####...#.###.####.##....##.####.#...###.##...#...#####..#...#.....###..#.#.#.##..#....#.#.#.#..###.
            ...#.#..####..##..##..#.###.......#..#.#..#...##....#...##.##.####..#...#.#...##.#..#.#.##...#.#.###
            ##.#.#...####.###..##...#...##.####.#..#..#.###.##..##..######.##.#.##.#.##.##.#...##...###.#.#.#.#.
            ....#....#.#.#.....#..###...#.####.#..#.###..#######..#.#...####...#...##...#.#..##.#.#..##.####.###
            ..##.#..#.....#.....#.##.#.#.##.#####.##....###.#.##.....##.#...###.#.#..#....#..#####.#.##.##.#.##.
            ###..##.#.#..##.##.##..##.....###.#.#..#.##....#...##..###.##.#...#.#.#.####.#..####...#.##.###..#..
            #....#.##..##.#..##...#.#.##..#.#..#..##.#....#.##....####..........##.##.....######.##...#.......#.
            #..#.##.###..##.#..#..#....#.####.#.....###.###....##...##..#..##.#####..#.#..########..#...####.#.#
            ###.#..##....###.#..##..###.##.#..#....####....#.#.....#.#.#######.###.#.#.##.######....#.#.#....##.
            ...#.##.###.##...##.####..#.###.##..#.#.###..###..#..#....###.###....##.....#..##..#..#.............
            ..#...###...#####.###.###.##.....###....#....###.###...#.#.#.#.##.#..#..####.#....#...#.#..#.##.####
            #####....#.....#...###..#.#....#..#.###..#.####........###.##.#........###.#.##...##..#..#.##.#....#
            .####.#..##.#.##.######.......#.#.#...#..#.#..#....#.##..#...#.#######....####.#..##.#..##..##.###..
            .#.##...#.##...#..#..#..###..#.#.......#.##..#.#..#.###..##.....#.#.#######.....##..#.##.#.###.#.##.
            ..##.#...##.###.##.#.#......##..##...##...###.#.##..#...#.#.####...#..##.#..#.##..##....#.##....###.
            #..############.#.#..#.##.##..#...#.......##.##..#.#..#..###....####...###.#.###..####.#.#.#..#..##.
            .#..#...#.##.##.#.##..#.##.###.....#..##...#.##......#.....##.#.##.##....####...#....##..##.####.##.
            #.###...##..#.##.##.######.#######.##..####.#..#.###.#.##...#.......#.######....###.#....##.#.##.#..
            #.##.........###....####.###.#.###.#....#.##.##.#.###...#.###..#.##.####.#######.###.#.##.##....#..#
            ##..#.##...##.#..###.#.##.#..##...#....###.###.#..#.##....#.###...#.#.#...#####..#....#..####.#.###.
            #.####.#..#.####.###.######.##..#.#...#.####..#...###.#...#.####.....###..#..#..#..###.#.##.###.#..#
            #.##.#.######.##.#.#.#.#.#....#...#...##.####.###.##.#..###....#........#...###.#.#..#.##..####..##.
            ...####.#.#.#.###........#####.###...#..#.#.......#....#..##.#.#.##.###...######..###.#...#.##.##.##
            .#..#.####.#..#.#....##..###.##..##.####..###....#.#..######...######.##.#.#.#.##.#....#.......##..#
            ...###..#####......#.###..#...#...#.##.###.##...#.....###########....##..##...#.#...#.....##.#..#.##
            #.........#.##.#.#....#......#..#.##.#..####...#..#.###.##.####...#..##.#.##.#.#.##.#.##...#.#.#.###
            .#.#..#....#......#.#.#.......#...#..#.#..##..#####.#.#.#####.....#..#####.#..#..##.#.#####..#.#...#
            ...#.#..#.##...#.#..#.##.##.##..#..#####.....#.###...##.#..#..#.....###.#.##..#.####.###.#..#..###.#
            .#.########.#.###..#....#.#####...######.#####.#..#.#####.#....#.....###..#..###.###.#..####..#....#
            ####.#.#.####.#.#..###.##.....#.####..##.#..#....#.####.###.##....#####.#.##.#.##.......#.#.#..#.#..
            ######..##.#...#.#.#####..#....#..#...###..#.#..##....#..##.....######..###.##.#.#.####.#.#...#..###
            .##......########.#..#.####.....####.#.###....##..##..##..#.##.#..#.##.###....##.#.....#.#.#.##...#.
            ###.#...#...##...#.####.###.###.#..#..#..#.###...#..####..####.#.#...##..#############.#..#......#.#
            #.##.#...#.######.####...#.#.#.#.#.######.#..####..#....##..###..#..#.###..#....##.##.#...####.#.#.#
            .....#.###.#...###..##.###.#.###.#....###....#..##.#.##..##.#####..##.######.#..##.##..#.#.##.#..#..
            ##..##.......#..####..##.#..#..#.##.#.#...#.....#.###...#.#....#.....#...#.#.#...##..#####.#.#.....#
            ..###...#.....##..#.#.##..#...######...#...##.#..#.####...#.#......##.####..#..#####.#.###...#..##..
            ###.#.#.....#..#.#..##.###...##.###..#...###.#.#..####...##..#.###..###.#...##.####.......##.##.....
            ##.#.#.#.#.......####.##..###.###..######..##.#...#..###..##.#..###.##....###..###.##....####.##..##
            .#..#####..#..###......##.#..##.......###.#.#......##.##.##.####.#..#####.#####.#.....#..#..#.#..#.#
            ...##.#.####.#...##.####..##.######.####.###...##...#.#..#####.....##.##...#.########.#..##.#..#.###
            #.##..#.##........#.#.##..#####..##.....##.#.###..######.###.#.#.....##..#........#....#.##.##......
            ##..#...#.####.#..####..###...#.#..#.....##.##.###...#.##.....#....#....####.#..####...#.###..#.#.#.
            ###.#.###...#.######....###..#...#.#..#....####.#.####..#.#.#.##.###..#..##..#.###.##.##....####...#
            #.##.#.#...##.....##.#..##....######....######.#.##.#...##.#.......#...##..##..##..###..#....####.#.
            ###.###..#.##.##.....#..#.#..###..###....#...####.#######.##.##.#..###.###.#.###.#.........####..##.
            ..###...####..#.##..####.##...##...#.####..#...#..#..####.##.#..#.....##..####.......#.#...##..##..#
            ###..#.#.##.#.#..###....##.###..##.#####....####...###.#.#..#.##.#...#..#######..#.#.#.##.###....###
            #....###...#....#...###.#.##.#....###...#####...#.#......#.##.##.#....###...#.#.#.#.#.#..###..#.####
            ..###...#.######.#..#..##.#....#####..####..###.#...####..#.#.#....#..#..###.#.##.....######.#..#..#
            ##...#.##.#.##.###.#.#.##.###..###.####.####...#.###.####.##......#.#..#.###..#......#.#.....#######
            .#.#..#..#..#.##...####...#...#.....##.#.##..###.##.##.#.#.##..#.##...###...###...##..####.#.###.#.#
            ..#...##.#.#.#.#..#####.####.#...##...#.#..####.....#.##..#.....#.#####.##...###..###....#.##...#..#
            ##...#...##.###.#.###...#..##..##.##..#.....##.###..#...##....###.#.##.#.#...###....#.##.###.####...
            ###...##.###.##........####...#.......##..###.##..#..####....###.#.###...#...###.#.#.#.###........#.
        """.trimIndent()
    }
}