package com.janus.buffers4Kotlin

/**
 * RingBuffer implementation
 */
class RingBuffer<T>(private val buffer: Array<Any?>) {
    val capacity: Int = buffer.size

    // as there is no unread item at the beginning,
    // the readSequence index should be after writeSequence
    private var readSequence = 0
    private var writeSequence = -1

    fun size() = (writeSequence - readSequence) + 1

    /**
     * insert new item
     * return false if the buffer is full
     */
    fun put(newItem: T): Boolean {
        // check if buffer is full
        if (isFull())
        // return false if unable to insert new item
            return false
        // increase the writeSequence
        // use mod operation to find the actual index as it is circular
        // assign / overwrite the cell value with newItem
        buffer[++writeSequence % capacity] = newItem
        return true
    }

    fun isFull() = size() == capacity

    /**
     * retrieve the next unread item
     * return null if there is no unread item
     */
    fun poll(): T? {
        // check if the buffer not empty
        if (writeSequence >= readSequence) {
            // return buffer value as type T from readSequence index
            // increase the readSequence index
            return buffer[readSequence++ % capacity] as T
        }
        // return null if there is no unread item
        return null
    }

    constructor(capacity: Int) : this(arrayOfNulls(capacity))
    constructor() : this(arrayOfNulls(256))
}