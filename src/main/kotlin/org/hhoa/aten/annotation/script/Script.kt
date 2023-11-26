package org.hhoa.aten.annotation.script

import java.lang.annotation.Inherited

/**
 * The smaller the priority value, the higher the priority.
 *
 * @property value
 * @constructor Create empty Priority
 */
object Priority {
    const val MAX = Int.MIN_VALUE
    const val MID = 0
    const val MIN = Int.MAX_VALUE
}

/**
 * Script.
 *
 * @author hhoa
 * @since 2023/6/21
 **/
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class Script(val priority: Int)
