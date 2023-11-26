package org.hhoa.aten.api.state

import org.hhoa.aten.api.exception.NoSuchMapStateException
import java.util.Objects

/**
 * Default implementation of KeyedStateStore that currently forwards state registration to a.
 */
class DefaultKeyedStateStore : KeyedStateStore {
    private val keyValueStatesByName = HashMap<String?, State?>()

    @Suppress("unchecked_cast")
    private fun <S : State?, V> getOrCreateKeyedState(stateDescriptor: StateDescriptor<S, V>): S {
        var kvState = keyValueStatesByName[stateDescriptor.name]
        if (kvState == null) {
            kvState = when (stateDescriptor.type) {
                StateDescriptor.Type.VALUE -> DefaultKeyValueState.create<V, S, S>(stateDescriptor)
                StateDescriptor.Type.LIST -> DefaultKeyListState.create<Any, V, S>(stateDescriptor)
                StateDescriptor.Type.MAP -> DefaultKeyMapState.create<Any, Any, V, S, S>(stateDescriptor)
                else -> {
                    throw NoSuchMapStateException("")
                }
            }
            keyValueStatesByName[stateDescriptor.name] = kvState
        }
        return kvState as S
    }

    override fun <T> getState(stateProperties: ValueStateDescriptor<T>): ValueState<T> {
        Objects.requireNonNull(stateProperties, "The state properties must not be null")
        return try {
            getOrCreateKeyedState(stateProperties)!!
        } catch (e: Exception) {
            throw RuntimeException("Error while getting state", e)
        }
    }

    override fun <T> getListState(stateProperties: ListStateDescriptor<T>): ListState<T> {
        Objects.requireNonNull(stateProperties, "The state properties must not be null")
        return try {
            getOrCreateKeyedState(stateProperties)!!
        } catch (e: Exception) {
            throw RuntimeException("Error while getting state", e)
        }
    }

    override fun <UK, UV> getMapState(stateProperties: MapStateDescriptor<UK, UV>): MapState<UK, UV> {
        Objects.requireNonNull(stateProperties, "The state properties must not be null")
        return try {
            getOrCreateKeyedState(stateProperties)!!
        } catch (e: Exception) {
            throw RuntimeException("Error while getting state", e)
        }
    }
}
