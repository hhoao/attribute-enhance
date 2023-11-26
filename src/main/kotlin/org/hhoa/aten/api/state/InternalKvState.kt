package org.hhoa.aten.api.state

/**
 * The `InternalKvState` is the root of the internal state type hierarchy, similar to the
 * [State] being the root of the public API state hierarchy.
 *
 *
 * The internal state classes give access to the namespace getters and setters and access to
 * additional functionality, like raw value access or state merging.
 *
 *
 * The public API state hierarchy is intended to be programmed against by Flink applications. The
 * internal state hierarchy holds all the auxiliary methods that are used by the runtime and not
 * intended to be used by user applications. These internal methods are considered of limited use to
 * users and only confusing, and are usually not regarded as stable across releases.
 *
 *
 * Each specific type in the internal state hierarchy extends the type from the public state
 * hierarchy:
 *
 * <pre>
 * State
 * |
 * +-------------------InternalKvState
 * |                         |
 * MergingState                   |
 * |                         |
 * +-----------------InternalMergingState
 * |                         |
 * +--------+------+                  |
 * |               |                  |
 * ReducingState    ListState        +-----+-----------------+
 * |               |            |                       |
 * +-----------+   +-----------   -----------------InternalListState
 * |                |
 * +---------InternalReducingState
</pre> *
 *
 * @param <N> The type of the namespace
</N> */
interface InternalKvState<N> : State {
    /**
     * Sets the current namespace, which will be used when using the state access methods.
     *
     * @param namespace The namespace.
     */
    fun setCurrentNamespace(namespace: N)
}
