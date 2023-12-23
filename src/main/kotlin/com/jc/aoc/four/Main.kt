package com.jc.aoc.four

import com.jc.aoc.util.AbstractAdventOfCodeDay
import com.jc.aoc.util.AdventOfCodeDay
import com.jc.aoc.util.validate
import kotlin.math.pow

fun main(args: Array<String>) {
    val dayFour = DayFour(args)
    dayFour.run()
}

data class ScratchGame(val winning: List<Int>, val yours: List<Int>)
data class ScratchGameCopies(val game: ScratchGame, var copies: Int)

class DayFour(args: Array<String>) : AbstractAdventOfCodeDay(args), AdventOfCodeDay {
    override fun getDay(): String = "four"

    override fun runInputPart1() {
        val rscLines = getResource("input")
        val parsedInput = parseScratchGames(rscLines)
        val actual = calculateWinningSumOfGames(parsedInput)
        println("result: $actual")
    }

    override fun runInputPart2() {
        val rscLines = getResource("input")
        val parsedInput = parseScratchGames(rscLines)
        val actual = calculateTotalScratchCardCopies(parsedInput)
        println("result: $actual")
    }

    override fun testSamplePart1() {
        val expected = 13
        val rscLines = getResource("sample_part1")
        val parsedInput = parseScratchGames(rscLines)
        val actual = calculateWinningSumOfGames(parsedInput)
        validate(expected, actual)
    }

    override fun testSamplePart2() {
        val expected = 30
        val rscLines = getResource("sample_part2")
        val parsedInput = parseScratchGames(rscLines)
        val actual = calculateTotalScratchCardCopies(parsedInput)
        validate(expected, actual)
    }

    private fun calculateWinningSumOfGames(games: List<ScratchGameCopies>): Int {
        return games.fold(0) { sum, g -> sum + calculateWinningSumOfGame(g.game) }
    }

    private fun calculateWinningSumOfGame(game: ScratchGame): Int {
        val matchNum = calculateWinningMatches(game)
        return if (matchNum == 0) 0 else 2.0.pow(matchNum - 1).toInt()
    }

    private fun calculateWinningMatches(game: ScratchGame): Int {
        return game.yours.fold(0) { sum, y ->
            if (game.winning.contains(y)) sum + 1
            else sum
        }
    }

    private fun calculateTotalScratchCardCopies(games: List<ScratchGameCopies>): Int {
        var index = 0
        while (index < games.size) {
            val game = games[index]
            val matchNum = calculateWinningMatches(game.game)
            if (matchNum != 0) {
                // bump copy count for matchNum number of next games
                for (i in index + 1..index + matchNum) {
                    // bump copies once per copy of current game
                    games[i].copies += game.copies
                }
            }
            index++
        }
        return games.fold(0) { sum, g -> sum + g.copies }
    }

    private fun parseScratchGames(rscLines: List<String>): List<ScratchGameCopies> {
        return rscLines.map { line ->
            val winningYoursSplit = line.split(":")[1].split("|")
            val winningStrList = winningYoursSplit[0].split(" ").filter { it.isNotBlank() }
            val yoursStrList = winningYoursSplit[1].split(" ").filter { it.isNotBlank() }
            ScratchGameCopies(
                game = ScratchGame(
                    winning = winningStrList.map { it.toInt() },
                    yours = yoursStrList.map { it.toInt() }
                ),
                copies = 1
            )
        }
    }
}