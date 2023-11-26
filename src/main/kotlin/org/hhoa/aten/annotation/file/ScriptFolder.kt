package org.hhoa.aten.annotation.file

import com.google.inject.BindingAnnotation


/**
 * Bind to the Script folder in DataFolder.
 * Used to get the Script folder through the inject of guice.
 *
 * @author hhoa
 * @since 2023/6/12
 **/

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@BindingAnnotation
annotation class ScriptFolder
