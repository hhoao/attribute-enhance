package org.hhoa.aten.api.state

import java.io.IOException

/**
 * [State] interface for partitioned single-value state. The value can be retrieved or
 * updated.
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
 * @param <T> Type of the value in the state.
</T> */
interface ValueState<T> : State {
    /**
     * Returns the current value for the state. When the state is not partitioned the returned value
     * is the same for all inputs in a given operator instance. If state partitioning is applied,
     * the value returned depends on the current operator input, as the operator maintains an
     * independent state for each partition.
     *
     *
     * If you didn't specify a default value when creating the [ValueStateDescriptor] this
     * will return `null` when no value was previously set using [.update].
     *
     * @return The state value corresponding to the current input.
     * @throws IOException Thrown if the system cannot access the state.
     */
    @Throws(IOException::class)
    fun value(): T

    /**
     * Updates the operator state accessible by [.value] to the given value. The next time
     * [.value] is called (for the same state partition) the returned state will represent
     * the updated value. When a partitioned state is updated with `null`, the state for the
     * current key will be removed and the default value is returned on the next access.
     *
     * @param value The new value for the state.
     * @throws IOException Thrown if the system cannot access the state.
     */
    @Throws(IOException::class)
    fun update(value: T)
}
