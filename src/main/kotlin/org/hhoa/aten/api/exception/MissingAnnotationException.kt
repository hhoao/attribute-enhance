package org.hhoa.aten.api.exception

/**
 * MissingAnnotationException.
 *
 * @author hhoa
 * @since 2023/6/20
 **/

/**
 * Missing annotation exception.
 *
 * @property annotation
 * @constructor Create empty Missing annotation exception
 */
class MissingAnnotationException(val annotation: Class<out Annotation>) : RuntimeException(){
    override val message: String
        get() = "Missing $annotation"
}
