package org.hhoa.aten.annotation.file

import com.google.inject.BindingAnnotation


/**
 * Config.
 *
 * @author hhoa
 * @since 2023/6/18
 **/

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@BindingAnnotation
annotation class Config(val value: String)
