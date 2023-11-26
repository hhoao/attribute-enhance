package org.hhoa.aten.api.state

/**
 * DefaultKeyValueState.
 *
 * @author hhoa
 * @since 2023/6/19
 */
class DefaultKeyValueState<N, T>(defaultValue: T) : AbstractKeyState<N, T>(defaultValue), ValueState<T?> {
    override fun value(): T? {
        return orDefault
    }

    override fun update(value: T?) {
        currentNamespaceValue = value
    }

    @Suppress("unchecked_cast")
    companion object {
        fun <SV, S : State?, IS : S?> create(stateDesc: StateDescriptor<S, SV>): IS {
            return DefaultKeyValueState<Any, SV?>(stateDesc.defaultValue) as IS
        }
    }
}
