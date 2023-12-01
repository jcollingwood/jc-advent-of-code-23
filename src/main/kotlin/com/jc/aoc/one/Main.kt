package com.jc.aoc.one

import com.jc.aoc.util.getResourceByLine
import com.jc.aoc.util.validate

fun main() {
//    testSamplePart1()
//    runInputPart1()
//    testSamplePart2()
    runInputPart2()
}

fun parseLineValuePart1(line: String): Int {
    val lineChars = line.toList()
    val firstDigit = lineChars.first { it.isDigit() }
    val lastDigit = lineChars.last { it.isDigit() }
    return "${firstDigit}${lastDigit}".toInt()
}

fun parseLineValuePart2(line: String): Int {
    val digitReplaceMap = mapOf(
        "zero" to "0",
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    val replacedLine = digitReplaceMap.keys.fold(line) { acc, s ->
        acc.replace(s, s + digitReplaceMap[s]!! + s)
    }
    return parseLineValuePart1(replacedLine)
}

fun parseAndSumLineValuesPart1(lines: List<String>): Int {
    return lines.fold(0) { acc, s -> acc + parseLineValuePart1(s) }
}

fun parseAndSumLineValuesPart2(lines: List<String>): Int {
    return lines.fold(0) { acc, s -> acc + parseLineValuePart2(s) }
}

fun runInputPart1() {
    val rscLines = getResourceByLine("/one/input.txt")
    val actual = parseAndSumLineValuesPart1(rscLines)
    println("result: $actual")
}

fun runInputPart2() {
    val rscLines = getResourceByLine("/one/input.txt")
    val actual = parseAndSumLineValuesPart2(rscLines)
    println("result: $actual")
}

fun testSamplePart1() {
    val expected = 142
    val rscLines = getResourceByLine("/one/sample_part1.txt")
    val actual = parseAndSumLineValuesPart1(rscLines)
    validate(expected, actual)
}


fun testSamplePart2() {
    val expected = 281
    val rscLines = getResourceByLine("/one/sample_part2.txt")
    val actual = parseAndSumLineValuesPart2(rscLines)
    validate(expected, actual)
}