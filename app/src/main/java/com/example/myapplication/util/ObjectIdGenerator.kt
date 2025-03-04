package com.example.myapplication.util

import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

object ObjectIdGenerator {
    private val counter = AtomicInteger(Random().nextInt(0xFFFFFF)) // Ensure 3-byte max value
    private val machineIdentifier = Random().nextInt(0xFFFFFF)
    private val processIdentifier = Random().nextInt(0xFFFF)

    fun generateObjectId(): String {
        val buffer = ByteBuffer.allocate(12)
        val timestamp = (System.currentTimeMillis() / 1000).toInt() // API 24+ friendly

        buffer.putInt(timestamp) // 4 bytes timestamp
        buffer.put((machineIdentifier shr 16).toByte()) // 3 bytes machine ID
        buffer.put((machineIdentifier shr 8).toByte())
        buffer.put(machineIdentifier.toByte())
        buffer.putShort(processIdentifier.toShort()) // 2 bytes process ID

        val count = counter.incrementAndGet() % 0xFFFFFF // Ensure counter fits in 3 bytes
        buffer.put((count shr 16).toByte()) // Store only the last 3 bytes
        buffer.put((count shr 8).toByte())
        buffer.put(count.toByte())

        return buffer.array().joinToString("") { "%02x".format(it) }
    }
}

fun main() {
    println(ObjectIdGenerator.generateObjectId()) // Example: "65e1bdf40a2f0bde894b317d"
}


