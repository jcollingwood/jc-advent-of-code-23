package com.jc.aoc.one

import com.jc.aoc.util.AdventOfCodeDay
import com.jc.aoc.util.getResourceByLine
import com.jc.aoc.util.validate

fun main() {
    val dayOne = DayOne()
    dayOne.testSamplePart1()
    dayOne.runInputPart1()
    dayOne.testSamplePart2()
    dayOne.runInputPart2()
}

class DayOne : AdventOfCodeDay {
    override fun getDay(): String = "one"

    override fun runInputPart1() {
        val rscLines = getResource("input")
        val actual = parseAndSumLineValuesPart1(rscLines)
        println("result: $actual")
    }

    override fun runInputPart2() {
        val rscLines = getResource("input")
        val actual = parseAndSumLineValuesPart2(rscLines)
        println("result: $actual")
    }

    override fun testSamplePart1() {
        val expected = 142
        val rscLines = getResource("sample_part1")
        val actual = parseAndSumLineValuesPart1(rscLines)
        validate(expected, actual)
    }

    override fun testSamplePart2() {
        val expected = 281
        val rscLines = getResource("sample_part2")
        val actual = parseAndSumLineValuesPart2(rscLines)
        validate(expected, actual)
    }

    private fun parseLineValuePart1(line: String): Int {
        val lineChars = line.toList()
        val firstDigit = lineChars.first { it.isDigit() }
        val lastDigit = lineChars.last { it.isDigit() }
        return "${firstDigit}${lastDigit}".toInt()
    }

    private fun parseLineValuePart2(line: String): Int {
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

    private fun parseAndSumLineValuesPart1(lines: List<String>): Int {
        return lines.fold(0) { acc, s -> acc + parseLineValuePart1(s) }
    }

    private fun parseAndSumLineValuesPart2(lines: List<String>): Int {
        return lines.fold(0) { acc, s -> acc + parseLineValuePart2(s) }
    }
}