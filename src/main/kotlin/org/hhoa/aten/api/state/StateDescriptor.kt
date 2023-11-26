package org.hhoa.aten.api.state

import org.hhoa.aten.utils.Preconditions
import java.io.Serial
import java.io.Serializable

/**
 * Base class for state descriptors. A `StateDescriptor` is used for creating partitioned
 * [State] in stateful operations.
 *
 *
 * Subclasses must correctly implement [.equals] and [.hashCode].
 *
 * @param <S> The type of the State objects created from this `StateDescriptor`.
 * @param <T> The type of the value of the state object described by this state descriptor.
</T></S> */
/**
 * The default value returned by the state when no other value is bound to a key.
 *
 */
abstract class StateDescriptor<S : State?, T> protected constructor(
    name: String,
    @field:Transient var defaultValue: T?
) : Serializable {
    /**
     * Returns the name of this `StateDescriptor`.
     */
    /**
     * Name that uniquely identifies state created from this StateDescriptor.
     */
    val name: String
    // ------------------------------------------------------------------------
    /**
     * Create a new `StateDescriptor` with the given name and the given type information.
     *
     * @param name         The name of the `StateDescriptor`.
     * @param defaultValue The default value that will be set when requesting state without setting
     * a value before.
     */
    init {
        this.name = Preconditions.checkNotNull(name, "name must not be null")
    }

    // ------------------------------------------------------------------------
    override fun hashCode(): Int {
        return name.hashCode() + 31 * javaClass.hashCode()
    }

    // ------------------------------------------------------------------------
    //  Standard Utils
    // ------------------------------------------------------------------------
    override fun equals(o: Any?): Boolean {
        return if (o === this) {
            true
        } else if (o != null && o.javaClass == this.javaClass) {
            val that = o as StateDescriptor<*, *>
            name == that.name
        } else {
            false
        }
    }

    abstract val type: Type?

    /**
     * An enumeration of the types of supported states. Used to identify the state type when writing
     * and restoring checkpoints and savepoints.
     */
    // IMPORTANT: Do not change the order of the elements in this enum, ordinal is used in
    // serialization
    enum class Type {
        VALUE,
        LIST,
        MAP
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
