package com.jc.aoc.util

interface AdventOfCodeDay {
    fun testSamplePart1()
    fun runInputPart1()
    fun testSamplePart2()
    fun runInputPart2()
    fun getDay(): String

    fun getResource(fileName: String): List<String> {
        return getResourceByLine("/${getDay()}/${fileName}.txt")
    }
}