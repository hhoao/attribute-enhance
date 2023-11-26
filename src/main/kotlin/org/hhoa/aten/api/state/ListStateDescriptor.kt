package org.hhoa.aten.api.state

import java.io.Serial

/**
 * A [StateDescriptor] for [ListState]. This can be used to create state where the type
 * is a list that can be appended and iterated over.
 *
 *
 * Using `ListState` is typically more efficient than manually maintaining a list in a
 * [ValueState], because the backing implementation can support efficient appends, rather than
 * replacing the full list on write.
 *
 *
 * To create keyed list state (on a KeyedStream), use [ ][<T>]
 * Creates a new `ListStateDescriptor` with the given name and list element type.
 *
 * @param name            The (unique) name for the state.
 */
class ListStateDescriptor<T>(name: String) : StateDescriptor<ListState<T>?, List<T>?>(name, null) {
    override val type: Type
        get() = Type.LIST

    companion object {
        @Serial
        private val serialVersionUID = 2L
    }
}
