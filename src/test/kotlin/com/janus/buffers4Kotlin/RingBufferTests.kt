package com.janus.buffers4Kotlin

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RingBufferTests {
    @Test
    fun `should able create new RingBuffer with default capacity (256)`() {
        val a = RingBuffer<Int>()
        assertEquals(256, a.capacity)
    }

    @Test
    fun `should able to create new RingBuffer with customized capacity (10)`() {
        val a = RingBuffer<Int>(10)
        assertEquals(10, a.capacity)
    }

    @Test
    fun `should throw exception if Ringbuffer input capacity less than 0`() {
        assertThrows<Exception> {
            RingBuffer<Int>(-1)
        }
    }

    @Test
    fun `should able to put new item in the buffer`() {
        val buffer = RingBuffer<Int>()
        assertFalse { buffer.isFull() }
        assertTrue { buffer.put(10) }
        assertEquals(1, buffer.size())
    }

    @Test
    fun `should return false if put new item when the buffer is fulled`() {
        val buffer = RingBuffer<Int>(0)
        assertFalse { buffer.put(10) }
        assertTrue { buffer.isFull() }
    }

    @Test
    fun `should able to retrieve next unread item`() {
        val buffer = RingBuffer<Int>()
        val expectedValue = 10
        assertTrue { buffer.put(expectedValue) }
        assertEquals(expectedValue, buffer.poll())
    }

    @Test
    fun `poll method should return null if there is no unread item`() {
        val buffer = RingBuffer<Int>()
        assertEquals(0, buffer.size())
        assertNull(buffer.poll())
    }

    @Test
    fun `should able to overwrite already read items`() {
        val buffer = RingBuffer<Int>(2)
        buffer.put(1)
        buffer.put(2)
        assertTrue { buffer.isFull() }
        assertEquals(1, buffer.poll())
        assertFalse { buffer.isFull() }
        buffer.put(3)
        assertTrue { buffer.isFull() }
        assertEquals(2, buffer.poll())
        assertEquals(3, buffer.poll())
        assertEquals(0, buffer.size())
    }
}