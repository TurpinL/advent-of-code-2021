val isTest = false
val xTargetMin = if (isTest) 20 else 60
val xTargetMax = if (isTest) 30 else 94
val xTargetRange =  xTargetMin..xTargetMax
val yTargetMin = if (isTest) -10 else -171
val yTargetMax = if (isTest) -5 else -136
val yTargetRange =  yTargetMin..yTargetMax

// Find all yVels that get it within the target for a given step
val validYSteps: MutableMap<Int/*Step*/, List<Int/*yVel*/>> = mutableMapOf()

for (targetStep in 0..342) {
    var yVel = yTargetMin

    while (true) {
        val yPos = calcYPos(yVel, targetStep)

        if (yPos > yTargetMax) {
            break
        } else if (yPos in yTargetRange) {
            validYSteps[targetStep] = listOf(yVel).plus(validYSteps[targetStep] ?: listOf())
        }

        yVel++
    }
}
//
//validYSteps.forEach { key, value ->
//    println("$key: $value")
//}

val validVels = mutableSetOf<Vel>()

// For each of those steps, find the valid xSteps that can take the probe to the target zone
validYSteps.forEach { (targetStep, yVels) ->
    var xVel = 1
    val validXVels = mutableListOf<Int>()

    while (xVel <= xTargetMax) {
        val xPos = calcXPos(xVel, targetStep)

        if (xPos in xTargetRange) {
            validXVels.add(xVel)
        }

        xVel++
    }

    validVels.addAll(
        yVels.flatMap { y ->
            validXVels.map { x -> Vel(x, y) }
        }
    )
}

println(validVels.count())
validVels.forEach {
    println(it)
}

data class Vel(
    val xVel: Int,
    val yVel: Int
) {
    override fun toString(): String {
        return "($xVel, $yVel)"
    }
}

fun calcYPos(startVel: Int, step: Int): Int {
    return startVel*step - (1 until step).sum()
}

// Only works for positive velocities
fun calcXPos(startVel: Int, step: Int): Int {
    val effectiveSteps = step.coerceAtMost(startVel)

    return startVel*effectiveSteps - (1 until effectiveSteps).sum()
}
