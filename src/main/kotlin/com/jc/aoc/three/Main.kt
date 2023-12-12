package com.jc.aoc.three

import com.jc.aoc.util.AdventOfCodeDay
import com.jc.aoc.util.validate

// TODO
fun main() {
    val dayThree = DayThree()
    dayThree.testSamplePart1()
//    dayThree.runInputPart1()
//    dayThree.testSamplePart2()
//    dayThree.runInputPart2()
}

class DayThree : AdventOfCodeDay {
    override fun getDay(): String = "three"

    override fun runInputPart1() {
        val rscLines = getResource("input")
        val actual = 0
        println("result: $actual")
    }

    override fun runInputPart2() {
        val rscLines = getResource("input")
        val actual = 0
        println("result: $actual")
    }

    override fun testSamplePart1() {
        val expected = 0
        val rscLines = getResource("sample_part1")
        val actual = 0
        validate(expected, actual)
    }

    override fun testSamplePart2() {
        val expected = 0
        val rscLines = getResource("sample_part2")
        val actual = 0
        validate(expected, actual)
    }
}