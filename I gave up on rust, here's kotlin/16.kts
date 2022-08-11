import kotlin.math.ceil

val isTest = true

var bits = input().map {
    val int = Integer.parseInt(it.toString(), 16)

    boolArrayToString(listOf(
        int and 0b1000 > 0,
        int and 0b0100 > 0,
        int and 0b0010 > 0,
        int and 0b0001 > 0,
    ))
}.joinToString("")


val bitStream = BitStream(bits)

readPacket()

println(bits)
println("pos ${bitStream.readHead} of ${bitStream.bits.length}")


fun readPacket() {
    val version = bitStream.readInt(3)
    val typeId = bitStream.readInt(3)

    if (typeId != 4) {
        val lengthTypeId = bitStream.read(1)

        println("v${version} Operator(")

        if (lengthTypeId == "0") {
            val length = bitStream.readInt(15)

            println("bits = ${bitStream.read(length)}")
        } else if (lengthTypeId == "1") {
            val numSubpackets = bitStream.readInt(11)

            println("bits = ${bitStream.read(numSubpackets * 11)}")
        }

        println(")")
    } else {
        println("v${version} Literal(${bitStream.readLiteral()})")
    }
}

//data class Packet (
//    val version: Int,
//    val typeId: Int,
//) {
//    override fun toString(): String {
//        return "v: $version, t: $typeId"
//    }
//}

class BitStream (
    val bits: String,
) {
    var readHead: Int = 0

    fun read(n: Int): String {
        val result = bits.substring(readHead, readHead + n)
        readHead += n
        return result
    }

    fun readInt(n: Int): Int {
        return binaryToInt(read(n))
    }

    fun readLiteral(): Int {
        var literalBits = ""
        while (true) {
            val nextBits = read(5)

            literalBits += nextBits.takeLast(4)

            if (nextBits.first() == '0') break
        }
        roundToNearestHexBoundary()

        return binaryToInt(literalBits)
    }

    fun roundToNearestHexBoundary() {
        readHead = (ceil(readHead / 4.0) * 4).toInt()
    }

    companion object {
        fun binaryToInt(binary: String): Int {
            return Integer.parseInt(binary, 2)
        }
    }
}

fun boolArrayToString(array: List<Boolean>): String {
    return array.map { if (it) '1' else '0' }.joinToString("")
}

fun input(): String {
    return if (isTest) {
        """EE00D40C823060""".trimIndent()
    } else {
        """""".trimIndent()
    }
}