package me.koendev.graph

abstract class BaseGraph

@Suppress("Unused", "MemberVisibilityCanBePrivate")
class Graph<NodeType, NameType>: BaseGraph() {
    var nodes: MutableMap<NameType, Node<NodeType>> = mutableMapOf()
    val edges: MutableList<Edge<NodeType>> = mutableListOf()

    fun getNode(name: NameType) = if (nodes[name] == null) throw NodeDoesNotExistsException() else nodes[name]!!

    fun addNode(node: Node<NodeType>, name: NameType) {
        if (name in nodes.keys) throw NodeExistsException()
        nodes[name] = node
    }

    fun removeNode(name: NameType) {
        if (nodes.remove(name) == null) throw NodeDoesNotExistsException()
    }

    fun addEdge(from: NameType, to: NameType, weight: Number = 1) {
        if (from !in nodes.keys || to !in nodes.keys) throw NodeDoesNotExistsException("Node $from or node $to does not exist in graph.")
        if (edges.any { it.a == nodes[from]!! && it.b == nodes[to]!! }) throw EdgeExistsException()
        if (edges.any { it.a == nodes[to]!! && it.b == nodes[from]!! }) throw EdgeExistsException()
        edges.add(Edge(nodes[from]!!, nodes[to]!!, weight))
    }

    fun removeEdge(from: NameType, to: NameType) {
        val edgesToRemove = edges.filter {(from == it.a && to == it.a) || (from == it.b && to == it.a)}

        if (edgesToRemove.isEmpty()) throw EdgeDoesNotExistException()

        edges.removeAll(edgesToRemove)
    }

    fun getNeighbors(name: NameType): MutableList<Node<NodeType>> {
        return getNeighbors(getNode(name))
    }

    fun getNeighbors(node: Node<NodeType>): MutableList<Node<NodeType>> {
        val neighbors = mutableListOf<Node<NodeType>>()

        neighbors += edges.filter { edge ->
            edge.a == node
        }.map { edge ->
            edge.b
        }

        neighbors += edges.filter { edge ->
            edge.b == node
        }.map { edge ->
            edge.a
        }

        return neighbors
    }

    fun removeDisconnectedNodes() {
        nodes = nodes.filter { node ->
            getNeighbors(node.value).isNotEmpty()
        }.toMutableMap()
    }
}