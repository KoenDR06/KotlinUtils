package me.koendev.utils

import kotlin.math.exp
import kotlin.math.ln

private const val BITS = 64
private const val INTEGER_BITS = 13

@Suppress("Unused", "MemberVisibilityCanBePrivate")
class FixedPoint {
    var number: Long = 0

    constructor(n: Int) {
        number = n.toLong() shl (BITS - INTEGER_BITS)
    }
    constructor(n: Long) {
        number = n
    }
    constructor(d: Double) {
        val data = java.lang.Double.doubleToRawLongBits(d)
        number = (data and 0xFFFFFFFFFFFFF) + 4503599627370496L

        val shift = 11 - INTEGER_BITS
        number = if (shift > 0) {
            number shl shift
        } else {
            number shr -shift
        }

        val exponent = ((data shr 52) and 0x7FF).toInt() - 1022

        number = if (exponent > 0) {
            number shl exponent
        } else {
            number shr -exponent
        }

        val signBit = (data shr 63).toInt()
        if (signBit == 1) {
            number = -number
        }
    }
    constructor() {
        number = 0
    }

    // Math operations
    fun floor(): FixedPoint {
        number = number shr (BITS - INTEGER_BITS - 1)
        number = number shl (BITS - INTEGER_BITS - 1)
        return this
    }
    fun ceil(): FixedPoint {
        number = number shr (BITS - INTEGER_BITS - 1)
        number++
        number = number shl (BITS - INTEGER_BITS - 1)

        return this
    }
    fun round(): FixedPoint {
        var absNumber = number
        var negative = 0
        if (number < 0) {
            negative = 1
            absNumber = absNumber.inv()
            absNumber += 1
        }

        val firstFractionBit = (absNumber shr (BITS - INTEGER_BITS - 2)) and 0x1

        return if (firstFractionBit != (1 xor (1-negative)).toLong()) {
            ceil()
        } else {
            floor()
        }
    }

    // Operators
    operator fun plus(other: FixedPoint): FixedPoint {
        return FixedPoint(number + other.number)
    }
    operator fun minus(other: FixedPoint): FixedPoint {
        return FixedPoint(number - other.number)
    }
    operator fun times(other: FixedPoint): FixedPoint {
        val a = number
        val aNegative = a < 0
        val ua = if (aNegative) (-a).toULong() else a.toULong()

        val b = other.number
        val bNegative = b < 0
        val ub = if (bNegative) (-b).toULong() else b.toULong()

        val sign = if (aNegative xor bNegative) -1 else 1

        var lower = 0UL
        var higher = 0UL

        for (i in 0..< BITS) {
            if ((ua shr i) and 1UL == 0UL) continue

            lower += ub shl i
            higher += ub shr (BITS -i)
        }

        return FixedPoint(  (lower shr (BITS - INTEGER_BITS - 1) or higher shl INTEGER_BITS).toLong() * sign )
    }

    fun toDouble(): Double {
        var res = 0.0
        var absNumber = number
        var negative = false
        if (number < 0) {
            negative = true

            absNumber = absNumber.inv()
            absNumber += 1
        }

        var i = BITS - 2
        while (i >= 0) {
            val bit = (absNumber shr i--) and 1
            if (bit == 1L) {
                res += exp(ln(2.0) * (i- BITS +3))
            }
        }

        res *= 1L shl (INTEGER_BITS -2)

        if (negative) {
            res *= -1
        }

        return res
    }

    override fun toString(): String {
        return toDouble().toString()
    }
}