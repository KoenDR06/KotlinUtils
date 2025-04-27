@file:Suppress("Unused")
package me.koendev.utils

import java.awt.Color
import java.io.File
import java.io.Serializable
import java.lang.Math.clamp
import java.math.BigInteger
import java.security.MessageDigest

fun <T> T.println(condition: (T) -> Boolean = { true }): T {
    if (condition(this)) println(this)
    return this
}

fun findLCM(a: Long, b: Long): Long {
    if (a == 0L || b == 0L) throw IllegalArgumentException("LCM of zero does not exist.")
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun List<Long>.lcm(): Long {
    var result = this[0]
    for (i in 1..< this.size) {
        result = findLCM(result, this[i])
    }
    return result
}

fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)

fun <T> List<T>.permutations(): List<List<T>> =
    if(isEmpty()) listOf(emptyList())
    else mutableListOf<List<T>>().also{ result ->
        for(i in this.indices){
            (this - this[i]).permutations().forEach {
                result.add(it + this[i])
            }
        }
    }

fun MutableList<Int>.diffs(): List<Int> {
    val res = mutableListOf<Int>()

    for (index in 0..< this.size-1) {
        res.add(this[index] - this[index+1])
    }
    return res
}

data class MutablePair<A, B>(
    var first: A,
    var second: B
) : Serializable

data class MutableTriple<A, B, C>(
    var first: A,
    var second: B,
    var third: C
) : Serializable

val LongProgression.length: Long
    get() = (this.last - this.first) / this.step + 1

fun pow(base: Int, exp: Int) = (1..exp).fold(1) { acc, _ -> acc * base }

fun pow(base: Long, exp: Long) = (1..exp).fold(1L) { acc, _ -> acc * base }

fun <T> List<T>.copy() = this.toMutableList()

fun Color.lerp(other: Color, t: Double): Color {
    if (clamp(t, 0.0, 1.0) != t) throw IllegalArgumentException("t has to be between 0 and 1")

    return Color(
        ((1 - t) * red + t * other.red).toInt(),
        ((1 - t) * green + t * other.green).toInt(),
        ((1 - t) * blue + t * other.blue).toInt()
    )
}

operator fun Color.times(scaler: Double): Color {
    return Color(
        clamp((red * scaler), 0.0, 255.0).toInt(),
        clamp((green * scaler), 0.0, 255.0).toInt(),
        clamp((blue * scaler), 0.0, 255.0).toInt(),
    )
}

fun Boolean.toInt() = if (this) 1 else 0

fun <T> List<T>.toCountedMap(): Map<T, Int> {
    val characterMap = mutableMapOf<T, Int>()

    this.forEach { char ->
        characterMap[char] = (characterMap[char] ?: 0) + 1
    }

    return characterMap
}

fun <T, LIST: Iterable<T>> LIST.assert(msg: String = "Assertion failed.", pred: (T) -> Boolean): LIST {
    if (!this.all { pred(it) }) throw AssertionError(msg)

    return this
}

fun dotEnv(): Map<String, String> {
    val res = mutableMapOf<String, String>()

    val data = File(".env").readText()
    var index = 0

    var readingKey = true
    var key = ""
    var value = ""

    while (index < data.length) {
        if (data[index] == '"' && !readingKey) {
            if (value.trim().isNotEmpty()) throw Exception("Cannot open string in the middle of a value.") else value=""

            index++
            while(data[index] != '"') {
                value += if (data[index] == '\\') {
                    when (data[index+1]) {
                        '\\' -> '\\'
                        'n' -> '\n'
                        't' -> '\t'
                        else -> throw Exception("Unrecognized escape sequence: '\\${data[index+1]}")
                    }
                } else data[index]
                index++
            }

            res[key.trim()] = value
            key = ""
            value = ""
            readingKey = true
            index++
            continue
        }

        if (data[index] == '#') {
            while(data[index] != '\n') index++
            index++
            continue
        }

        if (data[index] == '\n' && key != "") {
            if (readingKey) throw Exception("SYNTAX ERROR: Saw 'EOL', expected: '='")

            res[key.trim()] = value
            key = ""
            value = ""
            readingKey = true
            index++
            continue
        }

        if (data[index] == '=') {
            readingKey = false
            index++
            continue
        }

        if (readingKey) key += data[index] else value += data[index]

        index++
    }

    res[key.trim()] = value

    return res
}

operator fun Char.times(other: Int) = (0..< other).fold("") { acc, _ -> "$acc$this" }

val <T> List<T>.median: T
    get() = this[this.size / 2]