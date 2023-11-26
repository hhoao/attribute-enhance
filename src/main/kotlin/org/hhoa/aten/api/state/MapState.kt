package org.hhoa.aten.api.state

/**
 * [State] interface for partitioned key-value state. The key-value pair can be added, updated
 * and retrieved.
 *
 *
 * The state is accessed and modified by user functions, and checkpointed consistently by the
 * system as part of the distributed snapshots.
 *
 *
 * The state is only accessible by functions applied on a `KeyedStream`. The key is
 * automatically supplied by the system, so the function always sees the value mapped to the key of
 * the current element. That way, the system can handle stream and state partitioning consistently
 * together.
 *
 * @param <UK> Type of the keys in the state.
 * @param <UV> Type of the values in the state.
</UV></UK> */
interface MapState<UK, UV> : State {
    /**
     * Returns the current value associated with the given key.
     *
     * @param key The key of the mapping
     * @return The value of the mapping with the given key
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    operator fun get(key: UK): UV?

    /**
     * Associates a new value with the given key.
     *
     * @param key The key of the mapping
     * @param value The new value of the mapping
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    fun put(key: UK, value: UV)

    /**
     * Copies all of the mappings from the given map into the state.
     *
     * @param map The mappings to be stored in this state
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    fun putAll(map: Map<UK, UV>)

    /**
     * Deletes the mapping of the given key.
     *
     * @param key The key of the mapping
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    fun remove(key: UK)

    /**
     * Returns whether there exists the given mapping.
     *
     * @param key The key of the mapping
     * @return True if there exists a mapping whose key equals to the given key
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    operator fun contains(key: UK): Boolean

    /**
     * Returns all the mappings in the state.
     *
     * @return An iterable view of all the key-value pairs in the state.
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    fun entries(): Iterable<Map.Entry<UK, UV>>

    /**
     * Returns all the keys in the state.
     *
     * @return An iterable view of all the keys in the state.
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    fun keys(): Iterable<UK>

    /**
     * Returns all the values in the state.
     *
     * @return An iterable view of all the values in the state.
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    fun values(): Iterable<UV>

    /**
     * Iterates over all the mappings in the state.
     *
     * @return An iterator over all the mappings in the state
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    operator fun iterator(): Iterator<Map.Entry<UK, UV>>

    @get:Throws(Exception::class)
    val isEmpty: Boolean
}
