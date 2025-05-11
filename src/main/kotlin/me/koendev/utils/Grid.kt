package me.koendev.utils

@Suppress("Unused")
class Grid<T>: Iterable<Triple<Int,Int,T>> {
    constructor(input: List<List<T>>) {
        for (line in input) {
            grid.add(mutableListOf())
            for (char in line) {
                grid.last().add(char)
            }
        }
    }

    private var grid: MutableList<MutableList<T>> = mutableListOf()

    val height get() = grid.size
    val width get() = grid[0].size

    fun get(x: Int, y: Int): T? {
        if (x < 0 || x > grid[0].size-1) return null
        if (y < 0 || y > grid.size-1) return null
        return grid[y][x]
    }

    fun getRow(y: Int): List<T>? {
        if (y < 0 || y > grid.size-1) return null

        return grid[y]
    }

    fun getColumn(x: Int): List<T>? {
        if (x < 0 || x > grid[0].size-1) return null

        val res = mutableListOf<T>()

        for (row in grid) {
            res.add(row[x])
        }

        return res
    }

    fun find(item: T): Pair<Int, Int>? {
        for ((y, line) in grid.withIndex()) {
            for ((x, cell) in line.withIndex()) {
                if (cell == item) return Pair(x, y)
            }
        }
        return null
    }

    override fun iterator(): Iterator<Triple<Int,Int,T>> {
        val res = mutableListOf<Triple<Int,Int,T>>()
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, item ->
                res += Triple(x, y, item)
            }
        }
        return res.toList().iterator()
    }

    fun println() {
        grid.forEach {
            it.forEach { item ->
                print(item)
            }
            println("")
        }
    }
}
