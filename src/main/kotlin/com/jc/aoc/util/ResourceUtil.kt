package com.jc.aoc.util

import java.io.BufferedReader

fun getResourceAsText(path: String): String =
    object {}.javaClass.getResource(path)?.readText()!!

fun getResourceByLine(path: String): List<String> =
    object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.readLines()!!