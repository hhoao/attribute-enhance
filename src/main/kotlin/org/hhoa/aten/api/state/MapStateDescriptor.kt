package org.hhoa.aten.api.state

import java.io.Serial

/**
 * A [StateDescriptor] for [MapState]. This can be used to create state where the type
 * is a map that can be updated and iterated over.
 *
 *
 * Using `MapState` is typically more efficient than manually maintaining a map in a [ ], because the backing implementation can support efficient updates, rather then
 * replacing the full map on write.
 *
 * @param <UK> The type of the keys that can be added to the map state.</UK>
 * Create a new `MapStateDescriptor` with the given name and the given type information.
 *
 * @param name The name of the `MapStateDescriptor`.
 */
class MapStateDescriptor<UK, UV>(name: String) : StateDescriptor<MapState<UK, UV>?, Map<UK, UV>?>(name, null) {
    override val type: Type
        get() = Type.MAP

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
