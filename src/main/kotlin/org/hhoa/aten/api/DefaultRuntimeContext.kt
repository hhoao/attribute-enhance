package org.hhoa.aten.api

import org.hhoa.aten.api.state.DefaultKeyedStateStore
import org.hhoa.aten.api.state.KeyedStateStore
import org.hhoa.aten.api.state.ListState
import org.hhoa.aten.api.state.ListStateDescriptor
import org.hhoa.aten.api.state.MapState
import org.hhoa.aten.api.state.MapStateDescriptor
import org.hhoa.aten.api.state.ValueState
import org.hhoa.aten.api.state.ValueStateDescriptor

/**
 * DefaultRuntimeContext.
 */
class DefaultRuntimeContext : RuntimeContext {
    // ------------------------------------------------------------------------
    //  key/value state
    // ------------------------------------------------------------------------
    private val keyedStateStore: KeyedStateStore = DefaultKeyedStateStore()

    override fun <T> getState(stateProperties: ValueStateDescriptor<T>): ValueState<T> {
        return keyedStateStore.getState(stateProperties)
    }

    override fun <T> getListState(stateProperties: ListStateDescriptor<T>): ListState<T> {
        return keyedStateStore.getListState(stateProperties)
    }

    override fun <UK, UV> getMapState(stateProperties: MapStateDescriptor<UK, UV>): MapState<UK, UV> {
        return keyedStateStore.getMapState(stateProperties)
    }
}
