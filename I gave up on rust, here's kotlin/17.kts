import java.lang.Integer.max

val isTest = false
val xTargetMin = if (isTest) 20 else 60
val xTargetMax = if (isTest) 30 else 94
val xTargetRange =  xTargetMin..xTargetMax
val yTargetMin = if (isTest) -10 else -171
val yTargetMax = if (isTest) -5 else -136
val yTargetRange =  yTargetMin..yTargetMax

// Find which steps are valid for which xVelocities by iterating through them
// and seeing which steps they intersect the square on

// List of steps indexes where the x range can be hit with an integer xVel
val validSteps: MutableSet<Int> = mutableSetOf()

for (xStartingVel in 1..xTargetMax) {
    var xPos = 0
    var xVel = xStartingVel
    var currentStep = 1
    while (xPos <= xTargetMax && xVel > 0) {
        xPos += xVel
        if (xVel > 0) { xVel-- }

        if (xPos in xTargetRange) {
            validSteps.add(currentStep)
        }

        currentStep++
    }
}

// TODO: THIS ASSUMPTION IS FLAWED
// BECAUSE THE xVEL DETERIORATES EVERY STEP IS VALID. Maybe not for any target
// but it is for the example and the
validSteps.sorted()

// Find the max y velocity that'll be within the target for a given step
var bestYVel: Int = -1

for (targetStep in 300..4000) {
    var curYVel = 9

    while (true) {
        val yPos = calcYPos(curYVel, targetStep)

        if (yPos > yTargetMax) {
            break
        } else if (yPos in yTargetRange) {
            if (curYVel > bestYVel) {
                println("$targetStep, $bestYVel")
                bestYVel = max(curYVel, bestYVel)
            }
        }

        curYVel++
    }
}

bestYVel

// Find max height of that bestYVel
val maxHeight = 0
var currentStep = 1

while (true) {
    val yPos = calcYPos(bestYVel, currentStep)
    val nextYPos = calcYPos(bestYVel, currentStep+1)

    if (yPos > nextYPos) {
        println("$bestYVel Peak: $yPos")
        break
    }

    currentStep++
}

fun calcYPos(startVel: Int, step: Int): Int {
    return startVel*step - (1 until step).sum()
}

//for (targetStep in validSteps) {
//
//}

// Find yVelocities that will be within the y range on one of those steps
//
//while (yVel > 0 || yPos > yTargetMin) {
//    xPos += xVel
//
//    if (xVel > 0) { xVel-- }
//
//    yPos += yVel
//    yVel--
//
//    println("($xPos, $yPos)")
//}
