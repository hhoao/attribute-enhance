package org.hhoa.aten.api.state

/**
 * [State] interface for partitioned list state in Operations. The state is accessed and
 * modified by user functions, and checkpointed consistently by the system as part of the
 * distributed snapshots.
 *
 *
 * The state can be a keyed list state or an operator list state.
 *
 *
 * When it is a keyed list state, it is accessed by functions applied on a `KeyedStream`.
 * The key is automatically supplied by the system, so the function always sees the value mapped to
 * the key of the current element. That way, the system can handle stream and state partitioning
 * consistently together.
 *
 *
 * When it is an operator list state, the list is a collection of state items that are
 * independent from each other and eligible for redistribution across operator instances in case of
 * changed operator parallelism.
 *
 * @param <T> Type of values that this list state keeps.
</T> */
interface ListState<T> : State {
    /**
     * Updates the operator state accessible by [.get] by updating existing values to to the
     * given list of values. The next time [.get] is called (for the same state partition)
     * the returned state will represent the updated list.
     *
     *
     * If null or an empty list is passed in, the state value will be null.
     *
     * @param values The new values for the state.
     * @throws Exception The method may forward exception thrown internally (by I/O or functions).
     */
    @Throws(Exception::class)
    fun update(values: List<T>)

    /**
     * Updates the operator state accessible by [.get] by adding the given values to
     * existing list of values. The next time [.get] is called (for the same state
     * partition) the returned state will represent the updated list.
     *
     *
     * If null or an empty list is passed in, the state value remains unchanged.
     *
     * @param values The new values to be added to the state.
     * @throws Exception The method may forward exception thrown internally (by I/O or functions).
     */
    @Throws(Exception::class)
    fun addAll(values: List<T>)

    @Throws(Exception::class)
    fun get(): Iterable<T>?

    /**
     * Updates the operator state accessible by [.get] by adding the given value to the list
     * of values. The next time [.get] is called (for the same state partition) the returned
     * state will represent the updated list.
     *
     *
     * If null is passed in, the state value will remain unchanged.
     *
     * @param value The new value for the state.
     * @throws Exception Thrown if the system cannot access the state.
     */
    @Throws(Exception::class)
    fun add(value: T)
}
