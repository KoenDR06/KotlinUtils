package me.koendev.utils.graph

class Node<T>(var value: T) {
    override fun toString(): String {
        return value.toString()
    }
}