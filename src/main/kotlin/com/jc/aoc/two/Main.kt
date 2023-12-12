package com.jc.aoc.two

import com.jc.aoc.util.AdventOfCodeDay
import com.jc.aoc.util.validate

fun main() {
    val dayTwo = DayTwo()
//    dayTwo.testSamplePart1()
//    dayTwo.runInputPart1()
//    dayTwo.testSamplePart2()
    dayTwo.runInputPart2()
}

data class CubeGame(val gameNumber: Int, val rounds: List<CubeRound>)

data class CubeRound(val red: Int, val green: Int, val blue: Int)

class DayTwo : AdventOfCodeDay {
    override fun getDay(): String = "two"

    override fun runInputPart1() {
        val rscLines = getResource("input")
        val actual = sumPossibleGameIds(
            possibleRound = CubeRound(red = 12, green = 13, blue = 14),
            rscLines = rscLines
        )
        println("result: $actual")
    }

    override fun runInputPart2() {
        val rscLines = getResource("input")
        val actual = sumMinimumGamePowers(rscLines = rscLines)
        println("result: $actual")
    }

    override fun testSamplePart1() {
        val expected = 8
        val rscLines = getResource("sample_part1")
        val actual = sumPossibleGameIds(
            possibleRound = CubeRound(red = 12, green = 13, blue = 14),
            rscLines = rscLines
        )
        validate(expected, actual)
    }

    override fun testSamplePart2() {
        val expected = 2286
        val rscLines = getResource("sample_part2")
        val actual = sumMinimumGamePowers(rscLines = rscLines)
        validate(expected, actual)
    }

    private fun sumPossibleGameIds(possibleRound: CubeRound, rscLines: List<String>): Int {
        val games = parseInput(rscLines)
        return games.fold(0) { sumGameIds, gm ->
            if (gm.rounds.all { isRoundPossible(possibleRound, it) }) sumGameIds + gm.gameNumber else sumGameIds
        }
    }

    private fun isRoundPossible(possibleRound: CubeRound, actualRound: CubeRound): Boolean {
        return actualRound.red <= possibleRound.red &&
                actualRound.green <= possibleRound.green &&
                actualRound.blue <= possibleRound.blue
    }

    private fun sumMinimumGamePowers(rscLines: List<String>): Int {
        val games = parseInput(rscLines)
        val minRounds: List<CubeRound> = games.map { getMinimumCubes(it) }
        return minRounds.fold(0) { sum, round -> sum + getRoundPower(round) }
    }

    private fun getRoundPower(round: CubeRound): Int {
        return round.red * round.green * round.blue
    }

    private fun getMinimumCubes(game: CubeGame): CubeRound {
        return game.rounds.fold(CubeRound(red = 0, green = 0, blue = 0)) { currMinRound, round ->
            val maxRed = maxOf(currMinRound.red, round.red)
            val maxGreen = maxOf(currMinRound.green, round.green)
            val maxBlue = maxOf(currMinRound.blue, round.blue)
            CubeRound(red = maxRed, green = maxGreen, blue = maxBlue)
        }
    }

    private fun parseInput(rscLines: List<String>): List<CubeGame> {
        return rscLines.map {
            parseGame(it)
        }
    }

    private fun parseGame(line: String): CubeGame {
        val gameRegex = Regex("Game (\\d+): (.+)")
        val (gameNumber, roundsString) = gameRegex.find(line)!!.destructured
        val roundStrings: List<String> = roundsString.split(";")
        val rounds: List<CubeRound> = roundStrings.map {
            CubeRound(red = parseRedCount(it), green = parseGreenCount(it), blue = parseBlueCount(it))
        }
        return CubeGame(gameNumber = gameNumber.toInt(), rounds = rounds)
    }

    private fun parseRedCount(line: String): Int {
        return parseCount("red", line)
    }

    private fun parseGreenCount(line: String): Int {
        return parseCount("green", line)
    }

    private fun parseBlueCount(line: String): Int {
        return parseCount("blue", line)
    }

    private fun parseCount(color: String, line: String): Int {
        val regex = Regex("(\\d+) $color")
        return if (regex.containsMatchIn(line)) {
            val (c) = regex.find(line)!!.destructured
            c.toInt()
        } else {
            0
        }
    }
}