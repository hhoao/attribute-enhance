package org.hhoa.aten.utils

/**
 * Collector
 *
 * @author hhoa
 * @since 2023/6/13
 **/

interface Collector<T> {
    fun collect(record: T)

    /** Closes the collector. If any data was buffered, that data will be flushed.  */
    fun close()
}
