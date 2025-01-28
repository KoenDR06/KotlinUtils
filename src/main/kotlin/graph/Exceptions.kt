package me.koendev.graph

class NodeExistsException(reason: String = ""): Exception(reason)
class NodeDoesNotExistsException(reason: String = ""): Exception(reason)
class EdgeExistsException(reason: String = ""): Exception(reason)
class EdgeDoesNotExistException(reason: String = ""): Exception(reason)
