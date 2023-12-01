package com.jc.aoc.util

fun validate(expected: Any, actual: Any) {
    println("${if (expected == actual) "Success!" else "Fail :("} : [$expected] expected to match [$actual]")
}