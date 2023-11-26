package org.hhoa.aten.api.state

import java.io.Serial

/**
 * [StateDescriptor] for [ValueState]. This can be used to create partitioned value
 * state using [ &lt;p&gt;If you don&#39;t use one of the constructors that set a default value the value that you get when][ValueState.value]
 * Creates a new `ValueStateDescriptor` with the given name and type
 *
 *
 * If this constructor fails (because it is not possible to describe the type via a class),
 *
 * @param name      The (unique) name for the state.
 */
class ValueStateDescriptor<T>(name: String) : StateDescriptor<ValueState<T>?, T>(name, null) {
    override val type: Type
        get() = Type.VALUE

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
