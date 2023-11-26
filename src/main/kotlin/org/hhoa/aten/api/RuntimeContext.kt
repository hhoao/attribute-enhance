package org.hhoa.aten.api

import org.hhoa.aten.api.state.ListState
import org.hhoa.aten.api.state.ListStateDescriptor
import org.hhoa.aten.api.state.MapState
import org.hhoa.aten.api.state.MapStateDescriptor
import org.hhoa.aten.api.state.ValueState
import org.hhoa.aten.api.state.ValueStateDescriptor

/**
 * RuntimeContext.
 *
 * @author hhoa
 * @since 2023/6/19
 **/

interface RuntimeContext {
    /**
     * Gets a handle to the system's key/value state. The key/value state is only accessible if the
     * function is executed on a KeyedStream. On each access, the state exposes the value for the
     * key of the element currently processed by the function. Each function may have multiple
     * partitioned states, addressed with different names.
     * */
    fun <T> getState(stateProperties: ValueStateDescriptor<T>): ValueState<T>

    /**
     * Get list state.
     *
     * @param T
     * @param stateProperties
     * @return
     */
    fun <T> getListState(stateProperties: ListStateDescriptor<T>): ListState<T>

    /**
     * Get map state.
     *
     * @param UK
     * @param UV
     * @param stateProperties
     * @return
     */
    fun <UK, UV> getMapState(stateProperties: MapStateDescriptor<UK, UV>): MapState<UK, UV>
}
