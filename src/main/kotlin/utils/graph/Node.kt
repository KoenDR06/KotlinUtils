package me.koendev.graph

class Node<T>(var value: T) {
    override fun toString(): String {
        return value.toString()
    }
}