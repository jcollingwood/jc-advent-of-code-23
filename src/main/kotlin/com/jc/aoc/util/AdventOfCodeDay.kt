package com.jc.aoc.util

import java.util.*

enum class AdventOfCodePart(val part: Int) {
    ONE(1), TWO(2);

    companion object {
        fun getByNumber(part: Int): AdventOfCodePart {
            return AdventOfCodePart.values().first { it.part == part }
        }
    }
}

enum class AdventOfCodeOption {
    TEST, RUN
}

abstract class AbstractAdventOfCodeDay(args: Array<String>) : AdventOfCodeDay {
    val part: AdventOfCodePart
    val option: AdventOfCodeOption

    init {
        // idk, add validation if I actually care, just use it right, exception thrown is fine for now
        val partStr = args[0]
        val optionStr = args[1]
        part = AdventOfCodePart.getByNumber(partStr.toInt())
        option = AdventOfCodeOption.valueOf(optionStr.uppercase(Locale.getDefault()))
    }

    override fun run() {
        when (part) {
            AdventOfCodePart.ONE -> {
                when (option) {
                    AdventOfCodeOption.RUN -> runInputPart1()
                    AdventOfCodeOption.TEST -> testSamplePart1()
                }
            }

            AdventOfCodePart.TWO -> {
                when (option) {
                    AdventOfCodeOption.RUN -> runInputPart2()
                    AdventOfCodeOption.TEST -> testSamplePart2()
                }
            }
        }

    }

    abstract override fun testSamplePart1()
    abstract override fun runInputPart1()
    abstract override fun testSamplePart2()
    abstract override fun runInputPart2()
    abstract override fun getDay(): String

    fun getResource(fileName: String): List<String> {
        return getResourceByLine("/${getDay()}/${fileName}.txt")
    }
}

interface AdventOfCodeDay {
    fun run()
    fun testSamplePart1()
    fun runInputPart1()
    fun testSamplePart2()
    fun runInputPart2()
    fun getDay(): String
}