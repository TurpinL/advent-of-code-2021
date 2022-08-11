val isTest = true
val inputLines = input().lines()

val polymerTemplate = inputLines.first().toMutableList()

val rules = mutableMapOf<String, Char>()
inputLines.filter { it.contains("->") }
    .forEach {
        val parts = it.split(" -> ")
        rules[parts.first()] = parts.last().first()
    }

for (i in 0 until 10) {
    polymerTemplate.windowed(2)
        .map { it.joinToString("") }
        .withIndex()
        .reversed()
        .forEach { (index, pair) ->
            rules[pair]?.let {
                polymerTemplate.add(index + 1, it)
            }
        }

    println(polymerTemplate.joinToString(""))
}

val elementCount = mutableMapOf<Char, Long>()

polymerTemplate.forEach {
    elementCount[it] = (elementCount[it] ?: 0) + 1
}

println(elementCount)

(elementCount.values.maxOrNull() ?: 0) - (elementCount.values.minOrNull() ?: 0)

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