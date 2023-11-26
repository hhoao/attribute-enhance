package org.hhoa.aten.api.state

/**
 * A common class for all internal states in a single key state backend.
 */
abstract class AbstractKeyState<N, V> protected constructor(
    private val defaultValue: V
) : InternalKvState<N> {
    private val valuesForNamespaces: MutableMap<N, V> = HashMap()
    private var currentNamespace: N? = null
    var currentNamespaceValue: V? = null
    val orDefault: V
        get() = currentNamespaceValue ?: defaultValue

    override fun setCurrentNamespace(namespace: N) {
        if (currentNamespace == namespace) {
            return
        }
        if (currentNamespace != null) {
            if (currentNamespaceValue == null) {
                valuesForNamespaces.remove(currentNamespace)
            } else {
                valuesForNamespaces[currentNamespace!!] = currentNamespaceValue!!
            }
        }
        currentNamespaceValue = valuesForNamespaces[namespace]
        currentNamespace = namespace
    }

    override fun clear() {
        currentNamespaceValue = null
        valuesForNamespaces.remove(currentNamespace)
    }

    fun clearAllNamespaces() {
        currentNamespaceValue = null
        currentNamespace = null
        valuesForNamespaces.clear()
    }
}
