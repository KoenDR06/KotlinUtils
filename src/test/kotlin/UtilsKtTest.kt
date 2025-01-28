package me.koendev

import kotlin.math.pow
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class UtilsKtTest {
    @Test
    fun testLength() {
        assertTrue { (41L..56L step 7L).length == 3L }
        assertTrue { (52L..86L step 10L).length == 4L }
        assertTrue { (0L..18L step 2L).length == 10L }
        assertTrue { (18L downTo 0L step 2L).length == 10L }
    }

    @Test
    fun testPow() {
        for (i in 1..100) {
            val a = Random.nextInt(1, 10)
            val b = Random.nextInt(1, 10)

            assertTrue { pow(a,b) == a.toDouble().pow(b).toInt() }

            val c = Random.nextLong(1, 15)
            val d = Random.nextLong(1, 15)

            assertTrue { pow(c,d) == c.toDouble().pow(d.toDouble()).toLong() }
        }
    }
}