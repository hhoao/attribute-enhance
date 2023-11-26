package org.hhoa.aten.annotation.script

import java.lang.annotation.Inherited


/**
 * AttributeConfiguration.
 *
 * @author hhoa
 * @since 2023/6/15
 **/

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class Attribute(val key: String)
