package org.hhoa.aten.api


/**
 * AttributeConfiguration
 *
 * @author hhoa
 * @since 2023/7/4
 **/

class AttributeConfiguration(
    val name: String,
    val priority: Int,
    val labels: List<String>,
    val message: AttributeMessageConfiguration
) {
    class AttributeMessageConfiguration(val enable: Boolean, val format: String)
}
