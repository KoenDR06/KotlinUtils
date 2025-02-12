package me.koendev.utils

import java.awt.Color
import java.io.Serializable
import java.lang.Math.clamp
import java.math.BigInteger
import java.security.MessageDigest

@Suppress("Unused")
fun <T> T.println(condition: (T) -> Boolean = { true }): T {
    if (condition(this)) println(this)
    return this
}

@Suppress("Unused")
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

@Suppress("Unused")
fun List<Long>.lcm(): Long {
    var result = this[0]
    for (i in 1..< this.size) {
        result = findLCM(result, this[i])
    }
    return result
}

@Suppress("Unused")
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)

@Suppress("Unused")
fun <T> List<T>.permutations(): List<List<T>> =
    if(isEmpty()) listOf(emptyList())
    else mutableListOf<List<T>>().also{ result ->
        for(i in this.indices){
            (this - this[i]).permutations().forEach {
                result.add(it + this[i])
            }
        }
    }

@Suppress("Unused")
fun MutableList<Int>.diffs(): List<Int> {
    val res = mutableListOf<Int>()

    for (index in 0..< this.size-1) {
        res.add(this[index] - this[index+1])
    }
    return res
}

@Suppress("Unused")
data class MutablePair<A, B>(
    var first: A,
    var second: B
) : Serializable

@Suppress("Unused")
data class MutableTriple<A, B, C>(
    var first: A,
    var second: B,
    var third: C
) : Serializable

@Suppress("Unused")
val LongProgression.length: Long
    get() = (this.last - this.first) / this.step + 1

@Suppress("Unused")
fun pow(base: Int, exp: Int) = (1..exp).fold(1) { acc, _ -> acc * base }

@Suppress("Unused")
fun pow(base: Long, exp: Long) = (1..exp).fold(1L) { acc, _ -> acc * base }

@Suppress("Unused")
fun <T> List<T>.copy() = this.toMutableList()

@Suppress("Unused")
fun Color.lerp(other: Color, t: Double): Color {
    if (clamp(t, 0.0, 1.0) != t) throw IllegalArgumentException("t has to be between 0 and 1")

    return Color(
        ((1 - t) * red + t * other.red).toInt(),
        ((1 - t) * green + t * other.green).toInt(),
        ((1 - t) * blue + t * other.blue).toInt()
    )
}

@Suppress("Unused")
operator fun Color.times(scaler: Double): Color {
    return Color(
        clamp((red * scaler), 0.0, 255.0).toInt(),
        clamp((green * scaler), 0.0, 255.0).toInt(),
        clamp((blue * scaler), 0.0, 255.0).toInt(),
    )
}

@Suppress("Unused")
fun Boolean.toInt() = if (this) 1 else 0

@Suppress("Unused")
fun <T> List<T>.toCountedMap(): Map<T, Int> {
    val characterMap = mutableMapOf<T, Int>()

    this.forEach { char ->
        characterMap[char] = (characterMap[char] ?: 0) + 1
    }

    return characterMap
}