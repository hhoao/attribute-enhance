package org.hhoa.aten.api.state

/**
 * This interface contains methods for registering keyed state with a managed store.
 */
interface KeyedStateStore {
    /**
     * Gets a handle to the system's key/value state. The key/value state is only accessible if the
     * function is executed on a KeyedStream. On each access, the state exposes the value for the
     * key of the element currently processed by the function. Each function may have multiple
     * partitioned states, addressed with different names.
     *
     *
     * Because the scope of each value is the key of the currently processed element, and the
     * elements are distributed by the Flink runtime, the system can transparently scale out and
     * redistribute the state and KeyedStream.
     *
     *
     * The following code example shows how to implement a continuous counter that counts how
     * many times elements of a certain key occur, and emits an updated count for that element on
     * each occurrence.
     *
     * <pre>`DataStream<MyType> stream = ...;
     * KeyedStream<MyType> keyedStream = stream.keyBy("id");
     *
     * keyedStream.map(new RichMapFunction<MyType, Tuple2<MyType, Long>>() {
     *
     * private ValueState<Long> count;
     *
     * public void open(Configuration cfg) {
     * state = getRuntimeContext().getState(
     * new ValueStateDescriptor<Long>("count", LongSerializer.INSTANCE, 0L));
     * }
     *
     * public Tuple2<MyType, Long> map(MyType value) {
     * long count = state.value() + 1;
     * state.update(value);
     * return new Tuple2<>(value, count);
     * }
     * });
    `</pre> *
     *
     * @param stateProperties The descriptor defining the properties of the stats.
     * @param <T>             The type of value stored in the state.
     * @return The partitioned state object.
     * @throws UnsupportedOperationException Thrown, if no partitioned state is available for the
     * function (function is not part of a KeyedStream).
    </T> */
    fun <T> getState(stateProperties: ValueStateDescriptor<T>): ValueState<T>

    /**
     * Gets a handle to the system's key/value list state. This state is similar to the state
     * accessed via [.getState], but is optimized for state that holds
     * lists. One can adds elements to the list, or retrieve the list as a whole.
     *
     *
     * This state is only accessible if the function is executed on a KeyedStream.
     *
     * <pre>`DataStream<MyType> stream = ...;
     * KeyedStream<MyType> keyedStream = stream.keyBy("id");
     *
     * keyedStream.map(new RichFlatMapFunction<MyType, List<MyType>>() {
     *
     * private ListState<MyType> state;
     *
     * public void open(Configuration cfg) {
     * state = getRuntimeContext().getListState(
     * new ListStateDescriptor<>("myState", MyType.class));
     * }
     *
     * public void flatMap(MyType value, Collector<MyType> out) {
     * if (value.isDivider()) {
     * for (MyType t : state.get()) {
     * out.collect(t);
     * }
     * } else {
     * state.add(value);
     * }
     * }
     * });
    `</pre> *
     *
     * @param stateProperties The descriptor defining the properties of the stats.
     * @param <T>             The type of value stored in the state.
     * @return The partitioned state object.
     * @throws UnsupportedOperationException Thrown, if no partitioned state is available for the
     * function (function is not part os a KeyedStream).
    </T> */
    fun <T> getListState(stateProperties: ListStateDescriptor<T>): ListState<T>

    /**
     * Gets a handle to the system's key/value map state. This state is similar to the state
     * accessed via [.getState], but is optimized for state that is
     * composed of user-defined key-value pairs
     *
     *
     * This state is only accessible if the function is executed on a KeyedStream.
     *
     * <pre>`DataStream<MyType> stream = ...;
     * KeyedStream<MyType> keyedStream = stream.keyBy("id");
     *
     * keyedStream.map(new RichMapFunction<MyType, List<MyType>>() {
     *
     * private MapState<MyType, Long> state;
     *
     * public void open(Configuration cfg) {
     * state = getRuntimeContext().getMapState(
     * new MapStateDescriptor<>("sum", MyType.class, Long.class));
     * }
     *
     * public Tuple2<MyType, Long> map(MyType value) {
     * return new Tuple2<>(value, state.get(value));
     * }
     * });
     *
    `</pre> *
     *
     * @param stateProperties The descriptor defining the properties of the stats.
     * @param <UK>            The type of the user keys stored in the state.
     * @param <UV>            The type of the user values stored in the state.
     * @return The partitioned state object.
     * @throws UnsupportedOperationException Thrown, if no partitioned state is available for the
     * function (function is not part of a KeyedStream).
    </UV></UK> */
    fun <UK, UV> getMapState(stateProperties: MapStateDescriptor<UK, UV>): MapState<UK, UV>
}
