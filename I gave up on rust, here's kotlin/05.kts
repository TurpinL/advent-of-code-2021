import java.lang.Integer.max
import java.lang.Integer.min

input().lines()

val pattern = Regex("""^(\d+),(\d+) -> (\d+),(\d+)$""")

val pipes = input()
    .lines()
    .map {
        val caps = pattern.find(it)?.groupValues!!

        val start = Pair(caps[1].toInt(), caps[2].toInt())
        val end = Pair(caps[3].toInt(), caps[4].toInt())

        Pair(start, end)
    }

val xMax = pipes.maxOf {
    max(it.first.first, it.second.second)
}

val yMax = pipes.maxOf {
    max(it.first.second, it.second.second)
}

val seaFloor = Array(yMax + 1) { Array(xMax + 1) {0} }

for (pipe in pipes) {

    if (pipe.first.first == pipe.second.first) {
        // Vertical
        for (y in biDirRange(pipe.first.second, pipe.second.second)) {
            seaFloor[y][pipe.first.first] += 1
        }
    } else if (pipe.first.second == pipe.second.second) {
        // Horizontal
        for (x in biDirRange(pipe.first.first, pipe.second.first)) {
            seaFloor[pipe.first.second][x] += 1
        }
    } else {
        // Diagonal
        val range = biDirRange(pipe.first.first, pipe.second.first)
            .zip(biDirRange(pipe.first.second, pipe.second.second))

        for (coord in range) {
            seaFloor[coord.second][coord.first] += 1
        }
    }
}

// Print seafloor
for (row in seaFloor) {
    for (item in row) {
        if (item == 0) {
            print(". ")

        } else {
            print("$item ")
        }
    }
    println()
}

seaFloor.flatten().count {it > 1}

fun biDirRange(start: Int, end: Int): IntProgression {
    return if (start > end) {
        start.downTo(end)
    } else {
        start..end
    }
}

fun input(): String {
    return """
541,808 -> 108,808
982,23 -> 45,960
558,21 -> 558,318
907,877 -> 43,13
532,213 -> 532,801
599,387 -> 870,387
762,208 -> 78,208
739,527 -> 739,907
64,21 -> 64,958
258,267 -> 929,938
22,75 -> 725,778
347,950 -> 347,345
705,906 -> 61,906
53,16 -> 950,913
468,474 -> 475,481
567,602 -> 914,602
570,531 -> 570,530
180,307 -> 180,823
546,374 -> 390,374
750,142 -> 861,31
586,631 -> 905,950
971,680 -> 784,680
428,174 -> 352,174
825,676 -> 228,676
630,617 -> 70,617
156,912 -> 944,124
805,203 -> 25,983
726,808 -> 726,96
986,564 -> 908,642
594,293 -> 594,458
182,126 -> 182,476
979,43 -> 35,987
642,272 -> 642,446
759,690 -> 891,690
951,518 -> 161,518
357,769 -> 336,769
904,297 -> 904,533
326,332 -> 326,316
758,356 -> 654,460
432,425 -> 432,819
31,602 -> 31,421
318,555 -> 898,555
326,220 -> 777,671
708,957 -> 708,273
26,24 -> 974,972
341,172 -> 341,394
33,926 -> 864,95
486,324 -> 486,704
850,82 -> 132,800
62,506 -> 113,506
816,429 -> 816,141
184,17 -> 184,328
40,680 -> 30,670
640,294 -> 127,807
654,512 -> 654,296
722,301 -> 629,301
255,430 -> 811,430
376,385 -> 376,379
227,207 -> 227,947
363,533 -> 757,533
150,616 -> 150,284
943,100 -> 79,964
275,963 -> 275,461
409,768 -> 409,574
516,349 -> 516,656
19,666 -> 847,666
962,358 -> 962,907
781,789 -> 781,870
778,380 -> 501,657
895,29 -> 12,912
12,677 -> 12,761
614,728 -> 690,652
415,786 -> 778,423
683,84 -> 683,574
973,909 -> 75,11
854,791 -> 260,197
965,55 -> 559,55
624,542 -> 235,153
924,57 -> 16,965
540,736 -> 540,65
293,293 -> 929,293
587,176 -> 587,432
389,126 -> 389,590
267,149 -> 92,149
424,596 -> 697,323
412,697 -> 773,697
473,579 -> 310,416
149,175 -> 837,863
848,984 -> 159,295
608,613 -> 292,613
344,970 -> 546,970
589,531 -> 589,74
220,727 -> 220,831
651,240 -> 318,240
717,609 -> 847,479
917,954 -> 327,364
780,102 -> 228,654
201,95 -> 201,921
403,88 -> 403,79
755,76 -> 755,529
654,762 -> 654,97
957,988 -> 44,75
899,43 -> 51,891
855,892 -> 175,212
295,962 -> 613,962
284,800 -> 946,800
494,771 -> 793,472
212,799 -> 212,784
25,829 -> 824,30
277,135 -> 396,135
981,986 -> 187,192
794,88 -> 308,88
425,140 -> 73,140
155,900 -> 155,500
339,768 -> 339,621
720,407 -> 824,303
746,679 -> 746,490
672,632 -> 30,632
74,628 -> 49,628
534,770 -> 249,485
59,802 -> 605,256
481,543 -> 723,301
163,425 -> 947,425
10,782 -> 779,782
185,851 -> 68,968
536,479 -> 536,217
987,472 -> 960,472
802,623 -> 202,23
548,73 -> 548,838
711,678 -> 711,655
91,578 -> 91,162
803,27 -> 31,27
198,524 -> 282,608
45,585 -> 45,973
741,157 -> 317,581
263,843 -> 819,843
240,786 -> 240,272
453,148 -> 657,148
646,74 -> 646,758
367,521 -> 367,185
355,356 -> 503,356
306,780 -> 41,780
783,779 -> 513,779
81,886 -> 81,531
403,592 -> 69,926
575,458 -> 575,709
406,695 -> 406,563
342,690 -> 269,690
712,766 -> 712,693
929,608 -> 929,355
562,750 -> 571,759
353,780 -> 424,780
296,111 -> 944,759
341,28 -> 859,28
415,28 -> 415,153
104,16 -> 966,878
554,780 -> 726,780
749,645 -> 749,466
188,724 -> 933,724
806,185 -> 614,185
244,181 -> 682,181
104,937 -> 367,937
400,116 -> 183,333
749,94 -> 301,542
638,543 -> 811,716
694,727 -> 694,44
46,259 -> 46,721
467,748 -> 620,595
739,543 -> 391,543
734,454 -> 734,101
200,954 -> 200,654
592,358 -> 592,581
758,29 -> 758,401
386,685 -> 519,685
538,294 -> 298,294
82,269 -> 766,269
840,114 -> 332,114
924,542 -> 185,542
936,311 -> 369,878
820,724 -> 590,724
277,689 -> 68,689
76,634 -> 167,634
255,503 -> 144,503
753,915 -> 753,27
288,329 -> 513,554
729,297 -> 447,297
128,533 -> 128,530
252,718 -> 856,114
947,498 -> 312,498
142,917 -> 950,109
815,845 -> 770,845
863,98 -> 332,98
927,694 -> 927,276
682,232 -> 227,687
640,474 -> 840,274
98,37 -> 98,149
203,170 -> 99,170
736,956 -> 736,539
686,384 -> 882,580
976,152 -> 380,748
80,859 -> 80,208
687,252 -> 687,748
819,978 -> 101,260
17,722 -> 17,306
99,96 -> 99,929
713,757 -> 157,201
469,986 -> 469,393
813,210 -> 789,186
360,319 -> 360,43
777,707 -> 606,707
928,413 -> 380,961
566,850 -> 828,850
380,650 -> 717,650
863,889 -> 373,399
521,97 -> 967,97
12,989 -> 975,26
965,974 -> 965,848
639,331 -> 639,948
815,731 -> 235,151
823,369 -> 610,369
22,930 -> 898,54
635,113 -> 635,495
462,123 -> 771,123
445,274 -> 304,133
743,633 -> 43,633
811,267 -> 811,936
745,549 -> 636,549
321,720 -> 511,530
949,138 -> 308,138
563,34 -> 720,191
449,313 -> 966,830
857,308 -> 392,773
244,80 -> 820,80
212,345 -> 327,460
28,941 -> 28,22
122,988 -> 122,50
51,889 -> 880,60
328,161 -> 328,820
703,639 -> 40,639
107,47 -> 839,779
338,260 -> 828,750
947,304 -> 276,975
509,281 -> 281,281
200,42 -> 820,662
728,940 -> 728,897
511,770 -> 376,635
197,99 -> 929,99
699,648 -> 352,301
915,291 -> 256,950
641,586 -> 641,346
337,722 -> 965,722
739,92 -> 739,884
656,676 -> 649,676
369,450 -> 686,450
33,672 -> 409,296
336,979 -> 336,595
95,289 -> 888,289
286,128 -> 985,827
471,300 -> 899,300
824,959 -> 66,201
884,104 -> 166,822
562,681 -> 562,948
987,974 -> 77,64
61,47 -> 61,528
359,647 -> 709,647
34,398 -> 270,398
526,66 -> 257,335
744,545 -> 502,787
805,726 -> 728,803
184,749 -> 184,839
136,148 -> 842,148
538,244 -> 306,476
202,698 -> 958,698
264,519 -> 903,519
367,310 -> 26,310
391,747 -> 307,831
580,945 -> 706,945
199,776 -> 98,776
408,242 -> 408,474
929,706 -> 929,570
351,524 -> 221,524
773,783 -> 274,783
770,790 -> 770,653
572,182 -> 572,574
979,13 -> 29,963
175,454 -> 457,736
170,399 -> 170,202
570,406 -> 97,406
813,37 -> 556,37
80,886 -> 465,501
317,799 -> 876,799
602,399 -> 362,639
891,257 -> 445,257
346,275 -> 297,275
345,136 -> 345,755
252,460 -> 252,731
17,573 -> 729,573
901,838 -> 230,167
754,582 -> 754,970
415,964 -> 415,605
857,758 -> 857,612
319,613 -> 256,613
34,614 -> 34,802
443,118 -> 443,891
335,300 -> 80,45
284,340 -> 552,608
154,31 -> 33,31
440,720 -> 20,720
331,219 -> 311,219
163,83 -> 868,788
775,733 -> 775,284
859,240 -> 859,159
144,249 -> 105,210
710,809 -> 710,910
76,481 -> 76,86
825,603 -> 916,603
404,575 -> 740,575
612,427 -> 612,571
63,765 -> 63,538
979,170 -> 183,966
781,372 -> 911,372
237,732 -> 321,648
457,474 -> 954,971
887,110 -> 551,446
894,962 -> 48,116
276,534 -> 800,534
40,42 -> 950,952
986,941 -> 123,78
348,857 -> 899,857
57,728 -> 57,802
776,536 -> 776,462
683,618 -> 683,443
468,914 -> 750,914
420,129 -> 984,693
254,913 -> 166,913
832,737 -> 832,422
472,724 -> 12,724
483,916 -> 483,226
206,751 -> 206,250
890,901 -> 20,31
156,198 -> 883,925
972,367 -> 67,367
409,196 -> 320,196
59,188 -> 815,188
856,856 -> 856,756
84,871 -> 942,13
475,91 -> 475,784
363,578 -> 363,405
219,976 -> 219,717
243,25 -> 243,173
115,194 -> 462,194
91,960 -> 889,162
20,957 -> 915,62
60,955 -> 250,765
882,707 -> 267,92
122,626 -> 122,202
705,74 -> 147,632
418,122 -> 976,680
500,522 -> 936,522
715,652 -> 82,19
118,872 -> 118,479
918,70 -> 918,811
968,968 -> 347,347
985,479 -> 587,877
749,259 -> 749,841
475,102 -> 200,102
808,976 -> 515,976
761,726 -> 761,358
778,523 -> 729,474
266,251 -> 396,251
46,921 -> 914,921
384,424 -> 804,844
442,359 -> 646,155
929,774 -> 920,783
344,958 -> 344,281
33,173 -> 607,173
40,125 -> 897,982
345,640 -> 432,640
845,170 -> 403,612
763,84 -> 763,885
855,388 -> 123,388
861,858 -> 861,940
449,736 -> 97,384
576,592 -> 906,262
868,817 -> 868,633
14,100 -> 610,696
878,412 -> 416,412
43,427 -> 118,502
250,829 -> 770,829
814,444 -> 814,769
647,857 -> 528,857
648,102 -> 514,102
956,12 -> 887,12
665,957 -> 665,891
760,367 -> 178,949
704,524 -> 815,524
269,88 -> 322,88
414,881 -> 414,788
550,696 -> 550,788
624,367 -> 391,367
133,536 -> 432,835
635,154 -> 169,154
396,149 -> 396,166
796,505 -> 886,505
348,235 -> 530,235
428,851 -> 240,851
535,441 -> 637,441
661,175 -> 782,175
589,780 -> 99,290
771,746 -> 771,239
16,567 -> 821,567
320,572 -> 320,136
200,990 -> 513,990
984,987 -> 11,14
60,712 -> 60,137
629,405 -> 738,405
541,536 -> 541,225
250,579 -> 507,579
825,264 -> 974,264
380,832 -> 262,832
598,695 -> 598,159
716,782 -> 418,782
124,698 -> 713,698
930,83 -> 212,801
389,287 -> 344,287
571,788 -> 106,788
519,498 -> 135,114
281,761 -> 221,761
295,133 -> 295,654
686,960 -> 592,960
984,495 -> 984,913
677,751 -> 677,102
252,326 -> 252,824
921,500 -> 357,500
608,908 -> 608,381
587,878 -> 587,677
392,929 -> 449,929
239,444 -> 822,444
87,897 -> 252,897
865,887 -> 368,887
368,934 -> 368,308
64,950 -> 182,950
747,429 -> 636,540
378,109 -> 378,865
187,266 -> 856,935
159,769 -> 708,769
71,119 -> 892,940
629,825 -> 87,283
900,903 -> 900,656
749,703 -> 812,703
225,421 -> 842,421
15,896 -> 837,74
800,887 -> 58,145
456,798 -> 679,798
434,963 -> 434,166
508,491 -> 976,959
809,540 -> 809,614
624,632 -> 975,983
733,102 -> 195,640
83,691 -> 623,151
140,257 -> 29,257
429,934 -> 429,482
702,91 -> 702,137
986,185 -> 986,386
84,920 -> 448,920
927,779 -> 927,679
903,345 -> 546,345
303,523 -> 303,862
360,877 -> 360,202
544,593 -> 544,802
27,192 -> 27,837
105,24 -> 574,24
955,783 -> 556,384
416,85 -> 416,322
923,139 -> 553,139
527,523 -> 828,523
788,320 -> 949,320
391,652 -> 391,166
754,378 -> 607,378
563,409 -> 563,27
285,169 -> 285,883
16,10 -> 988,982
452,563 -> 452,479
881,800 -> 881,542
564,272 -> 457,272
887,441 -> 887,298
941,837 -> 119,15
606,137 -> 606,152
135,472 -> 135,322
881,775 -> 881,132
566,263 -> 406,103
912,696 -> 912,965
123,554 -> 123,911
735,737 -> 533,939
61,197 -> 534,197
91,856 -> 91,465
624,411 -> 624,247
607,899 -> 607,786
139,408 -> 466,735
89,274 -> 545,730
    """.trimIndent()
}

