package com.jc.aoc.three

import com.jc.aoc.util.AbstractAdventOfCodeDay
import com.jc.aoc.util.AdventOfCodeDay
import com.jc.aoc.util.validate

fun main(args: Array<String>) {
    val dayThree = DayThree(args)
    dayThree.run()
}

const val EMPTY_CH = '.'

class DayThree(args: Array<String>) : AbstractAdventOfCodeDay(args), AdventOfCodeDay {
    // extension function for Char to detect if symbol
    private fun Char.isSymbol(): Boolean = !(isDigit() || this == EMPTY_CH)

    override fun getDay(): String = "three"

    override fun runInputPart1() {
        val rscLines = getResource("input")
        val actual = calculateSumOfPartNumbers(rscLines)
        println("result: $actual")
    }

    override fun runInputPart2() {
        val rscLines = getResource("input")
        val actual = calculateSumPartRatios(rscLines)
        println("result: $actual")
    }

    override fun testSamplePart1() {
        val expected = 4361
        val rscLines = getResource("sample_part1")
        val actual = calculateSumOfPartNumbers(rscLines)
        validate(expected, actual)
    }

    override fun testSamplePart2() {
        val expected = 467835
        val rscLines = getResource("sample_part2")
        val actual = calculateSumPartRatios(rscLines)
        validate(expected, actual)
    }

    private fun calculateSumOfPartNumbers(linesStr: List<String>): Int {
        val lines = linesStr.map { it.toList() }

        var sum = 0

        // loop by char in row
        var row = 0
        val rowLength = lines.size
        while (row < rowLength) {
            val line = lines[row]
            val lineChars: List<Char> = line.toList()
            val colLength = lineChars.size
            var col = 0
            while (col < colLength) {
                val c = lineChars[col]

                // when number encountered:
                if (c.isDigit()) {
                    // get full number
                    // read ahead number until empty, symbol, or EOL then skip i to next index
                    var digitStr = c.toString()
                    var j = col + 1
                    while (j < colLength && lineChars[j].isDigit())
                        digitStr += lineChars[j++]

                    val digit = digitStr.toInt()

                    // check all adjacent cells for symbol
                    val adjacentCellHasSymbol = adjacentCellHasSymbol(
                        row = row,
                        colBefore = col - 1,
                        colAfter = j,
                        grid = lines
                    )

                    // if symbol encountered, add to sum
                    if (adjacentCellHasSymbol)
                        sum += digit

                    col = j
                }
                col++
            }
            row++
        }

        return sum
    }

    private fun calculateSumPartRatios(linesStr: List<String>): Int {
        val lines = linesStr.map { it.toList() }

        val indexMap: MutableMap<String, MutableList<Int>> = mutableMapOf()

        // loop by char in row
        var row = 0
        val rowLength = lines.size
        while (row < rowLength) {
            val line = lines[row]
            val lineChars: List<Char> = line.toList()
            val colLength = lineChars.size
            var col = 0
            while (col < colLength) {
                val c = lineChars[col]

                // when number encountered:
                if (c.isDigit()) {
                    // get full number
                    // read ahead number until empty, symbol, or EOL then skip i to next index
                    var digitStr = c.toString()
                    var j = col + 1
                    while (j < colLength && lineChars[j].isDigit())
                        digitStr += lineChars[j++]

                    val digit = digitStr.toInt()

                    // check all adjacent cells for symbol
                    val adjacentCellSymbolList = getAdjacentCellSymbolList(
                        row = row,
                        colBefore = col - 1,
                        colAfter = j,
                        grid = lines
                    )

                    // add digit to map of symbols and adjacent digits
                    // map of coordinates to a list of adjacent digits:
                    // e.g. 1-3 -> [467,35], ...
                    if (adjacentCellSymbolList.isNotEmpty())
                        adjacentCellSymbolList.forEach { pair ->
                            val key = "${pair.first}-${pair.second}"
                            val value = indexMap.getOrDefault(key, mutableListOf())
                            value.add(digit)
                            indexMap[key] = value
                        }

                    col = j
                }
                col++
            }
            row++
        }

        var sum = 0
        // map map of symbols and corresponding number(s), calculating ratio and adding to sum
        indexMap.values.forEach { value ->
            if (value.size == 2)
                sum += value[0] * value[1]
        }
        return sum
    }

    private fun adjacentCellHasSymbol(
        row: Int,
        colBefore: Int,
        colAfter: Int,
        grid: List<List<Char>>
    ): Boolean {
        // setting indexes, rows, and calculations
        val currRow = grid[row]
        val rowAbove = row - 1
        val rowBelow = row + 1
        val hasColBefore = colBefore > 0
        val hasColAfter = colAfter < currRow.size

        val rowHasSymbol: (List<Char>) -> Boolean = { r: List<Char> ->
            var hasSymbol = false
            if (hasColBefore && r[colBefore].isSymbol())
                hasSymbol = true
            else if (hasColAfter && r[colAfter].isSymbol())
                hasSymbol = true
            else {
                for (i in colBefore + 1..<colAfter) {
                    if (r[i].isSymbol())
                        hasSymbol = true
                }
            }
            hasSymbol
        }
        // before
        return (hasColBefore && currRow[colBefore].isSymbol()) ||
                // after
                (hasColAfter && currRow[colAfter].isSymbol()) ||
                // above
                (rowAbove > 0 && rowHasSymbol(grid[rowAbove])) ||
                // below
                (rowBelow < grid.size && rowHasSymbol(grid[rowBelow]))
    }

    private fun getAdjacentCellSymbolList(
        row: Int,
        colBefore: Int,
        colAfter: Int,
        grid: List<List<Char>>
    ): List<Pair<Int, Int>> {
        // setting indexes, rows, and calculations
        val currRow = grid[row]
        val rowAbove = row - 1
        val rowBelow = row + 1
        val hasColBefore = colBefore > 0
        val hasColAfter = colAfter < currRow.size

        val getPairsFromRow: (Int, List<Char>) -> List<Pair<Int, Int>> = { rowIdx: Int, r: List<Char> ->
            val pairs: MutableList<Pair<Int, Int>> = mutableListOf()
            if (hasColBefore && r[colBefore].isSymbol())
                pairs.add(Pair(rowIdx, colBefore))
            else if (hasColAfter && r[colAfter].isSymbol())
                pairs.add(Pair(rowIdx, colAfter))
            else {
                for (i in colBefore + 1..<colAfter) {
                    if (r[i].isSymbol())
                        pairs.add(Pair(rowIdx, i))
                }
            }
            pairs
        }

        val symbolPairs: MutableList<Pair<Int, Int>> = mutableListOf()
        // before
        if (hasColBefore && currRow[colBefore].isSymbol())
            symbolPairs.add(Pair(row, colBefore))
        // after
        if (hasColAfter && currRow[colAfter].isSymbol())
            symbolPairs.add(Pair(row, colAfter))
        // above
        if (rowAbove > 0)
            symbolPairs.addAll(getPairsFromRow(rowAbove, grid[rowAbove]))
        // below
        if (rowBelow < grid.size)
            symbolPairs.addAll(getPairsFromRow(rowBelow, grid[rowBelow]))

        return symbolPairs
    }
}