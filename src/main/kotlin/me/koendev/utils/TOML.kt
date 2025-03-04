@file:Suppress("Unused")
package me.koendev.utils

import java.io.File

fun Char.isTOMLWhitespace() = this in " \t"
fun String.isTOMLNewLine(index: Int) = this == "\n" || this[index].toString() + this[index+1] == "\r\n"

// https://toml.io/en/v1.0.0
fun loadTOML(file: File) {
    val data = file.readText()
    var index = 0

    var readingKey = true
    while (index < data.length) {


        index++
    }
}

fun main() {
    loadTOML(File("config.toml"))
}