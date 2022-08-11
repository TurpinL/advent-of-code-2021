import java.lang.Integer.max
import java.lang.Integer.min

val isTest = false

val pattern = Regex("""^(on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)${'$'}""")
val rebootSteps = input()
    .lines()
    .map {
        val captures = pattern.find(it)?.groupValues!!

        val xRange = listOf(captures[2].toInt(), captures[3].toInt())
        val yRange = listOf(captures[4].toInt(), captures[5].toInt())
        val zRange = listOf(captures[6].toInt(), captures[7].toInt())

        RebootStep(
            captures[1] == "on",
            Cube(
                xMin = xRange.minOrNull()!!,
                xMax = xRange.maxOrNull()!!,
                yMin = yRange.minOrNull()!!,
                yMax = yRange.maxOrNull()!!,
                zMin = zRange.minOrNull()!!,
                zMax = zRange.maxOrNull()!!,
            )
        )
    }

val reversedRebootSteps = rebootSteps.reversed()

val appliedStepCubes = mutableListOf<Cube>()
var onCount = 0L

for (curStep in reversedRebootSteps) {
    if (curStep.isOn) {
        // Start with the max that this step could light
        val newlyLit = curStep.cube.area()

        // Subtract all the squares in that area that have already been lit, or turned
        // off by future steps (we're applying steps backwards, remember)
        val intersects = appliedStepCubes.mapNotNull { it.intersect(curStep.cube) }
        onCount += newlyLit - area(intersects)
    }

    appliedStepCubes.add(curStep.cube)

    println("onCount = $onCount after $curStep")
}

println(onCount)

data class RebootStep(
    val isOn: Boolean,
    val cube: Cube,
)

data class Cube(
    val xMin: Int,
    val xMax: Int,
    val yMin: Int,
    val yMax: Int,
    val zMin: Int,
    val zMax: Int,
) {
    override fun toString(): String {
        return "Cube(x=$xMin..$xMax, y=$yMin..$yMax, z=$zMin..$zMax)"
    }

    fun area(): Long {
        return (xMax-xMin+1L) * (yMax-yMin+1L) * (zMax-zMin+1L)
    }

    fun intersect(other: Cube): Cube? {
        val xMin = max(xMin, other.xMin)
        val xMax = min(xMax, other.xMax)
        if (xMin > xMax) return null

        val yMin = max(yMin, other.yMin)
        val yMax = min(yMax, other.yMax)
        if (yMin > yMax) return null

        val zMin = max(zMin, other.zMin)
        val zMax = min(zMax, other.zMax)
        if (zMin > zMax) return null

        return Cube(xMin, xMax, yMin, yMax, zMin, zMax)
    }
}

fun area(cubes: List<Cube>): Long {
    if (cubes.isEmpty()) return 0

    val appliedCubes = mutableListOf<Cube>()
    var area = 0L

    for (curCube in cubes) {
        val curCubeArea = curCube.area()
        val intersects = appliedCubes.mapNotNull { it.intersect(curCube) }
        val intersectingArea = area(intersects)
        area += curCubeArea - intersectingArea
        appliedCubes.add(curCube)
    }

    return area
}

fun input(): String {
    return if (isTest) {
        """
            on x=-5..47,y=-31..22,z=-19..33
            on x=-44..5,y=-27..21,z=-14..35
            on x=-49..-1,y=-11..42,z=-10..38
            on x=-20..34,y=-40..6,z=-44..1
            off x=26..39,y=40..50,z=-2..11
            on x=-41..5,y=-41..6,z=-36..8
            off x=-43..-33,y=-45..-28,z=7..25
            on x=-33..15,y=-32..19,z=-34..11
            off x=35..47,y=-46..-34,z=-11..5
            on x=-14..36,y=-6..44,z=-16..29
            on x=-57795..-6158,y=29564..72030,z=20435..90618
            on x=36731..105352,y=-21140..28532,z=16094..90401
            on x=30999..107136,y=-53464..15513,z=8553..71215
            on x=13528..83982,y=-99403..-27377,z=-24141..23996
            on x=-72682..-12347,y=18159..111354,z=7391..80950
            on x=-1060..80757,y=-65301..-20884,z=-103788..-16709
            on x=-83015..-9461,y=-72160..-8347,z=-81239..-26856
            on x=-52752..22273,y=-49450..9096,z=54442..119054
            on x=-29982..40483,y=-108474..-28371,z=-24328..38471
            on x=-4958..62750,y=40422..118853,z=-7672..65583
            on x=55694..108686,y=-43367..46958,z=-26781..48729
            on x=-98497..-18186,y=-63569..3412,z=1232..88485
            on x=-726..56291,y=-62629..13224,z=18033..85226
            on x=-110886..-34664,y=-81338..-8658,z=8914..63723
            on x=-55829..24974,y=-16897..54165,z=-121762..-28058
            on x=-65152..-11147,y=22489..91432,z=-58782..1780
            on x=-120100..-32970,y=-46592..27473,z=-11695..61039
            on x=-18631..37533,y=-124565..-50804,z=-35667..28308
            on x=-57817..18248,y=49321..117703,z=5745..55881
            on x=14781..98692,y=-1341..70827,z=15753..70151
            on x=-34419..55919,y=-19626..40991,z=39015..114138
            on x=-60785..11593,y=-56135..2999,z=-95368..-26915
            on x=-32178..58085,y=17647..101866,z=-91405..-8878
            on x=-53655..12091,y=50097..105568,z=-75335..-4862
            on x=-111166..-40997,y=-71714..2688,z=5609..50954
            on x=-16602..70118,y=-98693..-44401,z=5197..76897
            on x=16383..101554,y=4615..83635,z=-44907..18747
            off x=-95822..-15171,y=-19987..48940,z=10804..104439
            on x=-89813..-14614,y=16069..88491,z=-3297..45228
            on x=41075..99376,y=-20427..49978,z=-52012..13762
            on x=-21330..50085,y=-17944..62733,z=-112280..-30197
            on x=-16478..35915,y=36008..118594,z=-7885..47086
            off x=-98156..-27851,y=-49952..43171,z=-99005..-8456
            off x=2032..69770,y=-71013..4824,z=7471..94418
            on x=43670..120875,y=-42068..12382,z=-24787..38892
            off x=37514..111226,y=-45862..25743,z=-16714..54663
            off x=25699..97951,y=-30668..59918,z=-15349..69697
            off x=-44271..17935,y=-9516..60759,z=49131..112598
            on x=-61695..-5813,y=40978..94975,z=8655..80240
            off x=-101086..-9439,y=-7088..67543,z=33935..83858
            off x=18020..114017,y=-48931..32606,z=21474..89843
            off x=-77139..10506,y=-89994..-18797,z=-80..59318
            off x=8476..79288,y=-75520..11602,z=-96624..-24783
            on x=-47488..-1262,y=24338..100707,z=16292..72967
            off x=-84341..13987,y=2429..92914,z=-90671..-1318
            off x=-37810..49457,y=-71013..-7894,z=-105357..-13188
            off x=-27365..46395,y=31009..98017,z=15428..76570
            off x=-70369..-16548,y=22648..78696,z=-1892..86821
            on x=-53470..21291,y=-120233..-33476,z=-44150..38147
            off x=-93533..-4276,y=-16170..68771,z=-104985..-24507
        """.trimIndent()
    } else {
        """
            on x=-48..6,y=-13..40,z=-12..35
            on x=-44..0,y=-24..28,z=-21..24
            on x=-26..28,y=-19..35,z=-34..15
            on x=-33..14,y=-48..1,z=-9..42
            on x=-15..34,y=-24..28,z=-15..37
            on x=-5..40,y=-43..11,z=0..47
            on x=-47..5,y=-19..34,z=-16..31
            on x=-49..2,y=-42..7,z=-11..43
            on x=-15..31,y=-6..48,z=-41..6
            on x=-48..-3,y=-18..36,z=-26..28
            off x=-22..-11,y=-42..-27,z=-29..-14
            on x=-19..30,y=-45..0,z=-26..21
            off x=-8..11,y=-4..15,z=-6..5
            on x=-36..9,y=-34..16,z=-48..4
            off x=-35..-22,y=-35..-17,z=-5..13
            on x=-17..30,y=-11..35,z=-18..35
            off x=4..21,y=-19..-8,z=-29..-18
            on x=-47..1,y=-5..41,z=-13..40
            off x=18..31,y=-47..-28,z=31..44
            on x=-39..11,y=-2..49,z=-7..37
            on x=59948..77225,y=-23808..-9387,z=7715..32589
            on x=50928..60239,y=-16409..-5204,z=51701..66341
            on x=-70857..-38119,y=-48880..-32940,z=-59519..-25446
            on x=34659..51798,y=36859..47631,z=-78056..-58506
            on x=24466..49551,y=-63135..-45703,z=40568..46891
            on x=36065..44964,y=-66141..-46768,z=15860..51738
            on x=-43929..-30796,y=54352..77794,z=-25429..2466
            on x=-21467..3054,y=30827..61109,z=-70969..-59488
            on x=74284..82380,y=-8243..5954,z=-21798..4298
            on x=-67855..-49197,y=44082..64316,z=-37876..-6139
            on x=3972..13181,y=-89954..-60769,z=-8964..17547
            on x=45537..72942,y=-52362..-40435,z=-34119..-19891
            on x=-2028..14997,y=35442..60991,z=61443..68750
            on x=-43916..-28061,y=-55465..-48755,z=42998..63681
            on x=73350..88457,y=-11874..16030,z=26440..31762
            on x=-65313..-56356,y=-53026..-32520,z=34300..39265
            on x=-32146..-12079,y=50972..72550,z=22248..46391
            on x=-46657..-43006,y=-16969..11204,z=-82344..-47383
            on x=35995..47370,y=35895..62150,z=39655..60420
            on x=-65653..-39728,y=-60635..-31172,z=-63220..-37129
            on x=-9288..10739,y=-16832..4120,z=-88004..-69447
            on x=-38863..-28980,y=-63737..-41900,z=37942..62217
            on x=-13517..-5187,y=52267..70255,z=37003..43218
            on x=-80174..-58479,y=-27669..-13937,z=22740..39119
            on x=40481..79327,y=-50686..-34109,z=-47873..-19251
            on x=-53141..-25290,y=4087..26829,z=-76669..-49306
            on x=47628..74073,y=30788..65007,z=-5729..24076
            on x=-27429..1716,y=-38795..-25619,z=-80470..-60991
            on x=-85532..-76247,y=-20583..-14388,z=-6180..6938
            on x=36136..54651,y=681..16673,z=54021..88347
            on x=-10839..2952,y=44009..73991,z=-76526..-37503
            on x=-80935..-54015,y=20375..47723,z=-44985..-11570
            on x=-29152..-17522,y=-79004..-70473,z=-19513..9075
            on x=42805..56719,y=-71746..-63383,z=-8020..9317
            on x=23827..62622,y=8817..33810,z=-63712..-56192
            on x=23854..50148,y=54657..74822,z=-35286..-16764
            on x=16361..35905,y=-50459..-25278,z=59114..81629
            on x=50416..73692,y=13668..34439,z=-58964..-28733
            on x=-16085..5956,y=33773..54756,z=53424..59946
            on x=29300..59677,y=16972..31580,z=-67574..-43566
            on x=-66498..-35764,y=-48191..-20365,z=-45669..-26894
            on x=-5249..15206,y=66472..87991,z=-25088..-6346
            on x=44940..47047,y=-7875..6164,z=48885..70603
            on x=20334..45224,y=-58330..-39597,z=-66985..-51764
            on x=-62110..-36926,y=-33844..-3332,z=-67989..-49276
            on x=-79537..-46973,y=-41387..-22193,z=-56295..-18343
            on x=35909..55950,y=-73326..-51004,z=951..14112
            on x=-3548..16851,y=-66843..-45057,z=-70220..-51667
            on x=69838..79527,y=-18994..9060,z=24208..40094
            on x=-33158..-21723,y=-39086..-22336,z=68420..89585
            on x=39270..59724,y=55673..65464,z=19151..38318
            on x=52496..59201,y=-65214..-44553,z=14672..41441
            on x=-80556..-51289,y=-6834..12651,z=45231..50265
            on x=-42529..-37256,y=-71786..-46047,z=-45160..-29457
            on x=-7292..20683,y=33964..38369,z=56266..81875
            on x=56554..94857,y=-9323..7628,z=-38191..-15246
            on x=-90239..-64199,y=-12106..4945,z=-31861..-6737
            on x=-70665..-37138,y=-3664..17652,z=-65566..-55631
            on x=39992..42010,y=5097..17932,z=-83176..-64675
            on x=2050..15132,y=55300..79725,z=24637..48177
            on x=-67167..-43932,y=-38348..-17508,z=32074..69487
            on x=-13982..9189,y=-64768..-47652,z=44539..72740
            on x=24036..50641,y=-80964..-50826,z=11307..32203
            on x=-15873..4045,y=71444..98664,z=5987..27175
            on x=34924..52262,y=-42488..-23727,z=-69151..-52471
            on x=-38716..-13415,y=-75701..-54513,z=16558..32557
            on x=54512..73804,y=-57104..-51952,z=6372..20935
            on x=-2472..18821,y=-93395..-65344,z=-27827..-21516
            on x=-30985..-14732,y=59384..91140,z=-27681..9023
            on x=-21901..13525,y=-22756..-10458,z=62818..95668
            on x=-56596..-42620,y=-9174..11095,z=40474..65553
            on x=9454..34644,y=45831..73068,z=-60309..-32554
            on x=-59975..-35542,y=21755..46912,z=-62049..-50380
            on x=50256..64742,y=44024..60232,z=11175..27704
            on x=70624..93118,y=-3971..24925,z=-24402..7603
            on x=-91350..-61496,y=28135..40274,z=-34236..-490
            on x=-62876..-48072,y=-66154..-41664,z=-25541..-16593
            on x=-74843..-43868,y=-44683..-21483,z=-61245..-27163
            on x=-21194..11087,y=32302..58516,z=-64798..-62134
            on x=-67768..-47768,y=-14556..2165,z=32174..62655
            on x=45627..72404,y=-10725..7085,z=-74867..-56057
            on x=73335..80103,y=-940..24549,z=-26652..-2057
            on x=-29611..-8953,y=-83755..-47428,z=-62239..-32609
            on x=-77451..-43059,y=-50733..-38309,z=-27638..-6618
            on x=2797..15028,y=-4395..18291,z=72354..89238
            on x=32357..54221,y=19376..54571,z=53654..62509
            on x=39407..45063,y=20798..36099,z=-84563..-49978
            on x=17999..24268,y=60988..69443,z=-48016..-24991
            on x=-12881..13621,y=6292..25612,z=61881..82751
            on x=5723..29564,y=61975..85143,z=-2144..27434
            on x=37079..51048,y=30137..58437,z=-69970..-36369
            on x=19115..52470,y=-70326..-47132,z=32756..58758
            on x=64631..72765,y=12149..20662,z=-43670..-21570
            on x=26118..42731,y=-56115..-18709,z=-70138..-48945
            on x=-17212..8538,y=45983..70176,z=33763..62277
            on x=-50017..-27326,y=-66494..-60174,z=29470..36944
            on x=-81143..-78005,y=-24265..-8891,z=-28874..1866
            on x=-4680..7425,y=-74141..-50733,z=-44837..-37602
            on x=-13433..2206,y=-62141..-38550,z=-73842..-51963
            on x=5912..29687,y=-51272..-21220,z=-89041..-61057
            on x=-90566..-72748,y=-4352..14510,z=161..15910
            on x=-66098..-42657,y=50253..73378,z=-26126..2146
            on x=-87456..-57132,y=-2793..20938,z=-43168..-26919
            on x=-65715..-49598,y=58414..71608,z=-6442..20355
            on x=-60479..-42891,y=-1414..4764,z=-76994..-58816
            on x=31247..51271,y=-72093..-55943,z=22587..41912
            on x=-48835..-24794,y=-69869..-51581,z=44188..61813
            on x=-79310..-52425,y=13545..46273,z=-1077..13779
            on x=-26198..-14119,y=-91381..-58786,z=22625..42542
            on x=-16634..-12884,y=-84573..-64477,z=-45007..-10241
            on x=8770..37879,y=-39990..-27335,z=-79869..-56641
            on x=-69417..-67919,y=-55482..-26599,z=4222..12461
            on x=-38614..-30984,y=46341..54464,z=44168..69759
            on x=28043..55260,y=-31640..-19321,z=-70069..-45405
            on x=-54428..-29734,y=-63361..-48536,z=29773..43221
            on x=-74758..-50576,y=41608..59761,z=-23506..-2964
            on x=-31899..-25641,y=45265..72201,z=-55652..-36338
            on x=-23602..-8956,y=49804..61138,z=-61100..-42764
            on x=-64012..-42795,y=28549..50310,z=-41432..-13826
            on x=-84716..-71838,y=-20336..-2119,z=-20114..-5422
            on x=10649..41863,y=38727..64335,z=-58210..-43041
            on x=60827..83146,y=-19276..97,z=900..39347
            on x=45494..67585,y=-10004..11155,z=-66469..-41468
            on x=-6167..-3234,y=-77297..-54226,z=-53843..-40669
            on x=-10346..9584,y=-89627..-65703,z=18788..41145
            on x=-13011..10106,y=-83728..-78513,z=-3855..20426
            on x=-21252..744,y=-59633..-51031,z=51691..60560
            on x=-81603..-57080,y=-43510..-15120,z=19352..47740
            on x=31643..53797,y=-33640..-20732,z=-57283..-52708
            on x=-72627..-41549,y=37174..63978,z=28579..51492
            on x=-915..969,y=-27860..-7753,z=64022..79251
            on x=12375..24026,y=-27076..421,z=-77551..-65430
            on x=42569..69220,y=-60197..-52691,z=19649..32410
            on x=11234..19988,y=56563..92456,z=-34414..-23000
            on x=32691..52925,y=-50395..-29538,z=44420..50843
            on x=72338..73748,y=-48473..-10825,z=-20831..2321
            on x=-36649..-23132,y=-80543..-55440,z=-52261..-18786
            on x=-81996..-66896,y=4406..25901,z=27102..33967
            on x=-88925..-66506,y=25211..33544,z=-43953..-16381
            on x=-22852..-16256,y=-81570..-64232,z=-2836..13133
            on x=-73125..-59973,y=-51071..-41288,z=2006..32523
            on x=11921..48558,y=-38014..-23538,z=52816..79133
            on x=-77276..-52350,y=10148..21580,z=-58080..-49079
            on x=-69682..-50301,y=39426..52352,z=6187..32903
            on x=16282..48921,y=17226..37966,z=60185..80807
            on x=46345..65705,y=-46417..-33395,z=-22827..-6690
            on x=-50394..-32686,y=-88333..-62954,z=-27024..3577
            on x=7140..33792,y=9767..46829,z=-81299..-61246
            on x=-9597..18989,y=46127..54334,z=58870..62950
            on x=-61738..-59895,y=-67751..-39782,z=3758..33083
            on x=18021..20588,y=528..22252,z=-79722..-66478
            on x=-33283..-16175,y=51647..88795,z=4289..38050
            on x=8418..34402,y=62940..75700,z=10937..39842
            on x=38066..69216,y=-61870..-52666,z=7001..21002
            on x=-14065..14210,y=26629..40175,z=64895..76326
            on x=-48905..-28984,y=-67332..-53830,z=27023..51056
            on x=-53349..-28532,y=-86113..-54385,z=-6640..3816
            on x=19927..40679,y=38888..50220,z=-66852..-37883
            on x=34822..58245,y=39419..56047,z=-48074..-35453
            on x=55519..71268,y=23251..51805,z=-43197..-34669
            on x=18366..31245,y=47455..57923,z=-63314..-40399
            on x=16809..44884,y=-72696..-51190,z=48656..59401
            on x=-65078..-41053,y=5543..11678,z=41590..60949
            on x=-66361..-47750,y=25542..52976,z=23459..52981
            on x=48492..73562,y=6501..24685,z=-55476..-47585
            on x=-5913..10423,y=-3513..23009,z=68060..95432
            on x=-36665..-16936,y=51458..79451,z=25675..41881
            on x=-50562..-20432,y=-36101..-8899,z=-75843..-59065
            on x=-10290..84,y=-91837..-60678,z=-45412..-30817
            on x=-43611..-27505,y=53544..74236,z=-51041..-27055
            on x=-84876..-69188,y=-9505..-2278,z=-19956..507
            on x=8912..34239,y=28948..46925,z=-84786..-57018
            on x=-66773..-42848,y=-15800..4160,z=-70245..-53902
            on x=1616..11581,y=-37586..-23422,z=-93635..-58351
            on x=-17716..-6166,y=23649..53084,z=-77414..-59720
            on x=66509..76291,y=-18788..14910,z=-44437..-15056
            on x=-62464..-54602,y=-40294..-18120,z=-48928..-29110
            on x=39077..56965,y=56879..78708,z=14990..24122
            on x=-53077..-36084,y=33702..54413,z=47657..68507
            on x=61520..70369,y=18056..32432,z=-44688..-27507
            on x=-59388..-42728,y=30479..52052,z=40767..50150
            on x=-92249..-67129,y=-10938..1941,z=-9913..7565
            on x=-25812..-1624,y=-71753..-51660,z=25844..40025
            on x=19649..48386,y=-41014..-23919,z=56427..70905
            on x=838..32095,y=-10367..1473,z=66348..91274
            on x=-38303..-27022,y=-67729..-47690,z=36025..54732
            on x=42482..59286,y=-63176..-53499,z=-88..20671
            on x=59687..88930,y=-26846..-7345,z=17360..48177
            on x=-78095..-59447,y=-20398..-14376,z=-24333..-9265
            on x=62819..83787,y=2948..33310,z=-45780..-18588
            on x=49058..64597,y=18216..37057,z=-53609..-38627
            on x=-84327..-56771,y=-23351..-11025,z=30765..50457
            on x=55753..73851,y=-58110..-46478,z=-27031..-4119
            on x=-73675..-36607,y=52154..67951,z=-1402..12004
            on x=16248..37146,y=-53084..-28138,z=-66661..-48262
            on x=-70764..-36150,y=-64440..-41720,z=25054..39650
            on x=27819..42917,y=-63843..-52656,z=25355..53094
            on x=-64547..-49557,y=-10197..13715,z=-70067..-57853
            on x=-40915..-28519,y=3206..31848,z=-76553..-55947
            on x=-70985..-47962,y=24987..33686,z=38467..44038
            off x=15513..36552,y=21011..48806,z=50480..70237
            on x=-8557..15504,y=-15503..7934,z=-80798..-65072
            off x=17025..42179,y=16804..40563,z=-84618..-62252
            on x=-61122..-32540,y=-62981..-60581,z=-30833..-5260
            on x=-78311..-49021,y=-36455..-26330,z=-51072..-34510
            on x=-66969..-49411,y=28962..57971,z=-45809..-25056
            off x=824..12291,y=-76958..-47671,z=35983..63416
            on x=-65217..-36321,y=-36157..-12489,z=48389..67998
            on x=-44405..-18201,y=34984..66207,z=44604..73727
            on x=-58665..-25483,y=-27490..-7360,z=54293..76012
            off x=-37186..-16747,y=-28709..-16239,z=-74836..-58410
            off x=-8413..11798,y=-91473..-63503,z=-28229..-8531
            on x=5456..33988,y=44489..56095,z=-67281..-58655
            off x=-30552..-13433,y=5159..26646,z=65720..88268
            on x=44920..60318,y=-66425..-43923,z=605..29139
            on x=22458..46724,y=-49969..-34674,z=54391..60192
            on x=-1703..12351,y=-87822..-72074,z=-7800..22480
            off x=47247..79163,y=35616..56606,z=-31735..-24909
            on x=11683..41539,y=-95733..-73977,z=-9681..16524
            on x=40812..55563,y=-33578..4865,z=64505..78897
            on x=-20036..15091,y=46995..59326,z=44884..77360
            on x=-62855..-39931,y=29479..48353,z=-51117..-45631
            off x=-37592..-25941,y=-34473..-13532,z=-87487..-51376
            on x=-64214..-39688,y=-1101..18763,z=-67677..-50206
            on x=-50153..-24356,y=-29250..-17320,z=-86169..-62094
            off x=-56208..-40169,y=62759..70069,z=-36809..-10249
            on x=30151..60484,y=-24287..-12395,z=46908..77720
            off x=48362..71117,y=-31069..-14945,z=-68867..-35565
            on x=-56693..-31524,y=-31916..-18443,z=43623..76949
            off x=-78038..-72092,y=-5149..16099,z=-44554..-9980
            on x=-577..25715,y=74108..84624,z=-4936..9637
            on x=-83031..-66932,y=14557..32000,z=6095..16066
            on x=-72143..-44594,y=-15004..-10698,z=38642..57085
            off x=-65045..-38272,y=-46823..-22080,z=39554..58068
            on x=34800..51119,y=51062..68236,z=-27991..-5201
            off x=-83416..-62320,y=16473..33611,z=12618..19218
            on x=25465..58543,y=24046..54475,z=53940..70358
            on x=-58596..-37090,y=57992..62107,z=-6659..7804
            off x=53454..82767,y=-21250..-1548,z=22267..54071
            off x=-79117..-60440,y=-63622..-38775,z=-8955..15394
            on x=-19536..6402,y=-69997..-47256,z=-62149..-43050
            off x=19297..31924,y=-76788..-55257,z=-63942..-44359
            on x=-5012..24323,y=63273..68750,z=-46613..-33442
            on x=35328..58056,y=31710..65651,z=-34982..-15237
            on x=-48192..-33863,y=-44827..-29117,z=-63183..-34387
            on x=-61029..-34553,y=-74065..-54148,z=9801..31057
            on x=-68960..-61393,y=95..25336,z=34991..51604
            off x=-22044..-2351,y=69872..96136,z=-10494..8286
            on x=1440..21268,y=5182..37527,z=74647..91431
            on x=44182..71181,y=-45262..-34205,z=46957..47598
            off x=-41795..-20178,y=-1286..12098,z=61369..93110
            on x=57490..63189,y=-38152..-27227,z=-51053..-39584
            off x=-63860..-30714,y=-37055..-32656,z=-69657..-35687
            on x=-74546..-47728,y=25851..61864,z=-37061..-17825
            off x=-24437..3458,y=53476..71199,z=50862..72294
            off x=-15044..-9328,y=59935..76501,z=-52378..-31589
            on x=-51655..-14304,y=57699..76496,z=-12947..16602
            off x=52532..82259,y=-42406..-24363,z=-1001..32079
            off x=41894..70607,y=4977..11111,z=43205..62849
            off x=-23814..11255,y=56959..88474,z=10473..38908
            on x=-13096..14972,y=71943..80228,z=-3028..18251
            on x=-27486..-15251,y=-86029..-54143,z=-26263..-4307
            off x=-91055..-73373,y=-3498..9631,z=4362..8282
            on x=-79152..-55232,y=22406..44403,z=13036..31288
            on x=8567..29474,y=-58818..-28928,z=-77992..-60316
            on x=2744..29974,y=-81832..-58217,z=-21689..10361
            on x=-40205..-20580,y=-6071..5493,z=-82965..-63879
            off x=-35449..-22473,y=-49684..-30492,z=63409..79484
            off x=-84114..-50896,y=22123..40386,z=19361..24349
            off x=-71399..-43033,y=-11721..9904,z=-72892..-49712
            off x=-35277..-5298,y=58757..71355,z=-59738..-31019
            on x=44485..68629,y=26563..44102,z=-40805..-27723
            on x=-69517..-45409,y=-16306..1823,z=-61229..-43343
            on x=14841..39436,y=40612..73880,z=-58735..-44090
            off x=-2942..16032,y=-91885..-70192,z=9003..32942
            on x=-55760..-25386,y=14465..39987,z=51195..77198
            on x=28238..56447,y=14808..33011,z=50390..79789
            off x=-92759..-55503,y=20524..34310,z=-23599..-9135
            off x=-15929..1979,y=61851..85083,z=29933..50070
            on x=9225..22713,y=1929..34143,z=-94179..-69874
            off x=7463..16496,y=-71732..-37732,z=-64922..-51893
            off x=44380..74744,y=-53121..-22361,z=-48742..-27977
            off x=46787..63581,y=45095..69265,z=-22173..4636
            on x=30769..50665,y=-27918..-20696,z=47470..75232
            on x=-21190..1756,y=-16222..1903,z=-90299..-75313
            off x=58199..86595,y=-9628..7129,z=27544..30672
            off x=-43628..-17336,y=39627..58311,z=-52613..-40574
            off x=-23083..11401,y=75825..85369,z=-2312..12405
            off x=62885..79995,y=-15595..2351,z=-17424..-9496
            on x=23650..50018,y=-61353..-36734,z=42359..60245
            on x=61306..78203,y=-9179..5865,z=23314..46707
            on x=42056..61788,y=45171..66184,z=12399..45341
            off x=-3793..18756,y=34734..58540,z=-67271..-59301
            on x=71182..92230,y=-10343..13589,z=-39267..-21565
            off x=8673..21938,y=60905..76276,z=32571..51045
            off x=3228..21181,y=-55369..-18898,z=-78377..-67410
            on x=34378..54624,y=-73840..-66767,z=-10049..8656
            off x=65360..88007,y=2060..32471,z=2333..11900
            off x=66657..87364,y=18928..42774,z=-42310..-3806
            on x=21572..43731,y=-54266..-44947,z=50141..72391
            on x=-21538..1482,y=-83291..-51586,z=-41117..-33639
            off x=3318..32804,y=42894..69299,z=46535..67272
            off x=-35245..-27958,y=5462..20046,z=-79841..-66010
            on x=-12513..-1547,y=-23669..7873,z=-83425..-67259
            off x=8909..41254,y=-93808..-74009,z=-4819..23133
            on x=-52200..-25835,y=15960..30377,z=42069..69104
            on x=36591..45810,y=-77352..-60366,z=-26787..1341
            on x=-77722..-44666,y=14773..30746,z=-61160..-34050
            off x=-46582..-35737,y=46260..81259,z=-46889..-11312
            off x=-71643..-56716,y=14635..38747,z=-61795..-43527
            off x=39240..74444,y=-51811..-27332,z=28772..58190
            on x=-58340..-31522,y=-66224..-44479,z=23007..48233
            on x=18236..38285,y=-66940..-47700,z=16110..34304
            off x=34869..53447,y=48325..63893,z=-51786..-34666
            on x=-79894..-43774,y=-62405..-31453,z=-29419..-7548
            off x=67003..70197,y=-47541..-15287,z=2946..37743
            off x=-27204..-12410,y=-92512..-65886,z=-29578..3556
            on x=23183..40749,y=62519..80268,z=-5202..3129
            off x=20602..41386,y=-70944..-53377,z=-62742..-28208
            off x=-81105..-54407,y=-9120..26155,z=40234..58924
            off x=-57955..-43394,y=37870..62660,z=-34290..-3179
            off x=-15827..1883,y=-31681..-25066,z=-87406..-55191
            off x=23264..45566,y=35567..48441,z=-73415..-42638
            on x=21830..28720,y=33175..51976,z=53057..78741
            on x=-36115..-7643,y=25118..34077,z=-85444..-59203
            off x=-68866..-54071,y=-19472..4363,z=36709..53410
            off x=6073..29768,y=-85905..-74717,z=-3954..25642
            on x=-7680..15355,y=-1280..15625,z=-81294..-59380
            off x=-23516..-11969,y=-67257..-53053,z=-62147..-53294
            on x=34228..67887,y=-51953..-31540,z=-63355..-36913
            on x=-31721..-17384,y=-84942..-69374,z=-25974..-4286
            off x=8617..24564,y=59625..82201,z=23799..48653
            off x=-74930..-51780,y=-20815..6541,z=28260..40960
            off x=20994..35874,y=1369..26269,z=-94505..-56912
            off x=64250..74106,y=-59689..-31772,z=-22200..-12533
            off x=-34889..-12423,y=-80068..-61748,z=-12141..6704
            off x=22706..39721,y=-7213..2019,z=-76680..-65357
            off x=12977..30454,y=44617..55414,z=-73673..-46816
            off x=18421..38150,y=-1478..28709,z=-90428..-66015
            off x=33023..58631,y=-48706..-29814,z=-56022..-45248
            off x=-3236..5109,y=69874..87161,z=-9318..17274
            off x=-20560..6052,y=24119..47449,z=-75118..-53474
            off x=56672..71355,y=6287..18607,z=35879..60566
            off x=-45868..-26774,y=-87658..-63722,z=5520..22277
            on x=-21496..-12868,y=55261..74982,z=-54403..-48761
            on x=-82371..-70162,y=10539..29774,z=-42087..-16035
            off x=18165..28274,y=30681..46858,z=57064..64733
            on x=-31955..-11653,y=-83050..-70993,z=-8732..1596
            off x=54188..82626,y=39940..66005,z=-2835..7547
            on x=39725..68296,y=-14146..21379,z=52332..70999
            on x=68729..79757,y=-26981..-12892,z=-1005..18847
            on x=-89230..-55343,y=-7039..21914,z=-41530..-22135
            off x=-74761..-52782,y=-54591..-34050,z=-23588..1682
            off x=4588..38441,y=-45595..-25312,z=67291..73998
            on x=26181..42562,y=13286..21558,z=45515..72382
            on x=-48975..-32434,y=25381..44962,z=-68715..-38026
            on x=-56512..-28986,y=-61324..-45574,z=-52067..-31443
            off x=-97629..-71452,y=-9969..7612,z=-11415..17685
            on x=20053..34006,y=20406..41231,z=59488..73021
            off x=21900..51510,y=-17327..-4900,z=-82994..-71598
            off x=-65182..-35081,y=-45600..-23546,z=53314..59882
            on x=-14048..11980,y=-47734..-10267,z=-76957..-57926
            on x=10786..24964,y=-33833..-22273,z=72870..80431
            on x=71392..76011,y=18505..26763,z=-30097..-7811
            off x=5721..35848,y=-70691..-61922,z=-35016..-20564
            off x=37966..67044,y=-74895..-38819,z=-11378..24619
            off x=58047..87227,y=-46608..-26640,z=-4670..13147
            off x=-9970..19768,y=-9007..16226,z=-81209..-68216
            on x=58592..69296,y=-19057..6596,z=45774..66020
            on x=-81176..-70642,y=-7521..12540,z=11474..31489
            off x=51735..63155,y=-28647..7980,z=44811..64656
            off x=-59355..-39775,y=22154..55986,z=45934..68592
            off x=42785..65066,y=20097..30749,z=42691..67585
            on x=-78686..-56664,y=36058..51298,z=-46490..-32545
            on x=-80311..-57248,y=16685..26536,z=-1660..26257
            off x=-470..23335,y=-68058..-53549,z=-60120..-50237
            off x=-70801..-48788,y=21764..49044,z=12615..26202
            off x=-73980..-59126,y=10795..38972,z=29913..34771
            on x=77138..91326,y=-5153..22559,z=-8498..22392
            off x=-9498..4859,y=71751..94896,z=-15102..22812
            on x=23752..41322,y=36509..66598,z=-55985..-33177
            on x=32328..55201,y=48991..74159,z=14820..44520
            off x=-60929..-31013,y=47356..55252,z=-50579..-32438
            on x=-72486..-55704,y=-51776..-33226,z=11436..25787
            on x=-15912..-9994,y=-52777..-22910,z=-68881..-54558
            off x=2253..11637,y=73284..83993,z=12224..22275
            off x=18360..27676,y=11300..36836,z=-92072..-72331
            off x=37724..70027,y=-59694..-27177,z=-56310..-28993
            on x=-59217..-23702,y=-51823..-38981,z=-66296..-33273
            on x=31180..53449,y=14631..30890,z=-79341..-48736
            off x=-36056..-19761,y=-83624..-55630,z=-24386..-9717
            off x=-1545..9148,y=21897..31020,z=-88337..-70051
            off x=-32989..-9724,y=37526..63857,z=-70387..-56746
            off x=29903..51146,y=-63108..-50491,z=32857..41295
            on x=-46331..-35553,y=-3044..13283,z=62129..77317
            on x=33499..52700,y=-70674..-45077,z=-39327..-19649
            on x=58723..70030,y=15231..36339,z=-60353..-33130
            on x=-989..14391,y=-78770..-65176,z=22254..51047
            off x=66499..89004,y=7540..23824,z=-22765..-8879
            off x=44823..66466,y=-10101..16210,z=53856..61509
        """.trimIndent()
    }
}