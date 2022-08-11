val isTest = false
val inputLines = input().lines()

val polymerTemplate = inputLines.first().toMutableList()

// Split the polymer template into overlapping pairs
// then keep track of the occurrences of each pair
// This grouping allows us to process the rules more efficiently
// We lose track of the exact order, but it doesn't matter for
// what the answer wants
var polymerPairBuckets = mutableMapOf<String, Long>()
polymerTemplate
    .windowed(2)
    .map { it.joinToString("") }
    .forEach {
        polymerPairBuckets[it] = (polymerPairBuckets[it] ?: 0) + 1
    }

val rules = mutableMapOf<String, Char>()
inputLines.filter { it.contains("->") }
    .forEach {
        val parts = it.split(" -> ")
        rules[parts.first()] = parts.last().first()
    }

// To work out the next set of polymerPairBuckets
// each pair WITHOUT a rule stays the same
// each pair WITH a rule is consumed and creates 2 new pairs
for (i in 0 until 40) {
    val tempBuckets = mutableMapOf<String, Long>()

    polymerPairBuckets.forEach {
        if (rules.containsKey(it.key)) {
            val newPairA = "${it.key.first()}${rules[it.key]}"
            val newPairB = "${rules[it.key]}${it.key.last()}"

            tempBuckets[newPairA] = (tempBuckets[newPairA] ?: 0) + it.value
            tempBuckets[newPairB] = (tempBuckets[newPairB] ?: 0) + it.value
        } else {

            tempBuckets[it.key] = (tempBuckets[it.key] ?: 0) + it.value
        }
    }

    polymerPairBuckets = tempBuckets
}

// Since the pairs in the buckets overlap, we just
// count the first character of each pair. Since the second
// character will be the first character from another pair.
val elementCount = mutableMapOf<Char, Long>()
// The only exception to this is the very last pair, but we
// because the rules only insert characters int pairs we know
// the last character will always be the same. So we must manually
// start the count of that character at 1.
elementCount[polymerTemplate.last()] = 1

polymerPairBuckets.forEach {
    val char = it.key.first()
    elementCount[char] = (elementCount[char] ?: 0) + 1*it.value
}

println(elementCount)
println(elementCount.values.maxOrNull()!! - elementCount.values.minOrNull()!!)

fun input(): String {
    return if (isTest) {
        """
NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C
        """.trimIndent()
    } else {
        """
            PKHOVVOSCNVHHCVVCBOH

            NO -> B
            PV -> P
            OC -> K
            SC -> K
            FK -> P
            PO -> P
            FC -> V
            KN -> V
            CN -> O
            CB -> K
            NF -> K
            CO -> F
            SK -> F
            VO -> B
            SF -> F
            PB -> F
            FF -> C
            HC -> P
            PF -> B
            OP -> B
            OO -> V
            OK -> N
            KB -> H
            PN -> V
            PP -> N
            FV -> S
            BO -> O
            HN -> C
            FP -> F
            BP -> B
            HB -> N
            VC -> F
            PC -> V
            FO -> O
            OH -> S
            FH -> B
            HK -> B
            BC -> F
            ON -> K
            FN -> N
            NN -> O
            PH -> P
            KS -> H
            HV -> F
            BK -> O
            NP -> S
            CC -> H
            KV -> V
            NB -> C
            NS -> S
            KO -> V
            NK -> H
            HO -> C
            KC -> P
            VH -> C
            VK -> O
            CP -> K
            BS -> N
            BB -> F
            VV -> K
            SH -> O
            SO -> N
            VF -> K
            NV -> K
            SV -> O
            NH -> C
            VS -> N
            OF -> N
            SP -> C
            HP -> O
            NC -> V
            KP -> B
            KH -> O
            SN -> S
            CS -> N
            FB -> P
            OB -> H
            VP -> B
            CH -> O
            BF -> B
            PK -> S
            CF -> V
            CV -> S
            VB -> P
            CK -> H
            PS -> N
            SS -> C
            OS -> P
            OV -> F
            VN -> V
            BV -> V
            HF -> B
            FS -> O
            BN -> K
            SB -> N
            HH -> S
            BH -> S
            KK -> H
            HS -> K
            KF -> V
        """.trimIndent()
    }
}