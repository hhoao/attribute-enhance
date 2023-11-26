package org.hhoa.aten.api.state

import java.util.Collections

/**
 * A [MapState] which keeps value for a single key at a time.
 */
class DefaultKeyMapState<UK, UV> protected constructor(
    defaultValue: MutableMap<UK, UV>
) : AbstractKeyState<UK, MutableMap<UK, UV>>(defaultValue), MapState<UK, UV> {
    @Throws(Exception::class)
    override fun get(key: UK): UV? {
        return currentNamespaceValue?.get(key)
    }

    override fun put(key: UK, value: UV) {
        initIfNull()
        currentNamespaceValue?.put(key, value)
    }

    override fun putAll(map: Map<UK, UV>) {
        initIfNull()
        currentNamespaceValue?.putAll(map)
    }

    private fun initIfNull() {
        if (currentNamespaceValue == null) {
            currentNamespaceValue = HashMap()
        }
    }

    @Throws(Exception::class)
    override fun remove(key: UK) {
        if (currentNamespaceValue == null) {
            return
        }
        currentNamespaceValue!!.remove(key)
        if (currentNamespaceValue!!.isEmpty()) {
            clear()
        }
    }

    @Throws(Exception::class)
    override fun contains(key: UK): Boolean {
        return currentNamespaceValue != null && currentNamespaceValue!!.containsKey(key)
    }

    override fun entries(): Iterable<Map.Entry<UK, UV>> {
        return currentNamespaceValue?.entries ?: emptySet()
    }

    override fun keys(): Iterable<UK> {
        return currentNamespaceValue?.keys ?: emptySet()
    }

    override fun values(): Iterable<UV> {
        return currentNamespaceValue?.values ?: emptySet()
    }

    override fun iterator(): Iterator<Map.Entry<UK, UV>> {
        return currentNamespaceValue?.entries?.iterator() ?: Collections.emptyIterator()
    }

    override val isEmpty: Boolean
        get() = currentNamespaceValue == null || currentNamespaceValue!!.isEmpty()

    @Suppress("unchecked_cast")
    companion object {
        fun <UK, UV, SV, S : State?, IS : S?> create(
            stateDesc: StateDescriptor<S, SV>
        ): IS {
            return DefaultKeyMapState(
                stateDesc.defaultValue as MutableMap<UK, UV>
            ) as IS
        }
    }
}
