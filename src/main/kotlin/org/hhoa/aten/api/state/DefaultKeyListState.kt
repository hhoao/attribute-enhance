package org.hhoa.aten.api.state

import com.google.common.base.Preconditions

/**
 * A [ListState] which keeps value for a single key at a time.
 */
internal open class DefaultKeyListState<N, T> protected constructor(
    defaultValue: MutableList<T>
) : AbstractKeyState<N, MutableList<T>>(defaultValue), ListState<T> {
    override fun update(values: List<T>) {
        Preconditions.checkNotNull(values)
        clear()
        for (value in values) {
            add(value)
        }
    }

    override fun addAll(values: List<T>) {
        if (Preconditions.checkNotNull(values).isEmpty()) {
            return
        }
        for (value in values) {
            add(value)
        }
    }

    override fun add(value: T) {
        Preconditions.checkNotNull<T>(value)
        initIfNull()
        currentNamespaceValue?.add(value)
    }

    @Throws(Exception::class)
    override fun get(): Iterable<T>? {
        return currentNamespaceValue
    }

    protected fun merge(target: MutableList<T>, source: List<T>?): List<T> {
        target.addAll(source!!)
        return target
    }

    private fun initIfNull() {
        if (currentNamespaceValue == null) {
            currentNamespaceValue = ArrayList()
        }
    }

    @Suppress("unchecked_cast")
    companion object {
        fun <T, SV, S : State?> create(stateDesc: StateDescriptor<S, SV>): S {
            return DefaultKeyListState<Any, T>(stateDesc.defaultValue as MutableList<T>) as S
        }
    }
}
