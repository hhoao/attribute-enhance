package org.hhoa.aten.annotation.file

import com.google.inject.BindingAnnotation


/**
 * Bind with DataFolder of plugin.
 *
 * @author hhoa
 * @since 2023/6/12
 **/

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@BindingAnnotation
annotation class DataFolder
