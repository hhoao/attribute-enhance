package org.hhoa.aten.utils

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

/**
 * A collection of static utility methods to validate input.
 *
 *
 * This class is modelled after Google Guava's Preconditions class, and partly takes code from
 * that class. We add this code to the Flink code base in order to reduce external dependencies.
 */
object Preconditions {
    /**
     * Ensures that the given object reference is not null. Upon violation, a `NullPointerException` with no message is thrown.
     *
     * @param reference The object reference
     * @return The object reference itself (generically typed).
     * @throws NullPointerException Thrown, if the passed reference was null.
     */
    fun <T> checkNotNull(reference: T?): T {
        if (reference == null) {
            throw NullPointerException()
        }
        return reference
    }

    /**
     * Ensures that the given object reference is not null. Upon violation, a `NullPointerException` with the given message is thrown.
     *
     * @param reference    The object reference
     * @param errorMessage The message for the `NullPointerException` that is thrown if the
     * check fails.
     * @return The object reference itself (generically typed).
     * @throws NullPointerException Thrown, if the passed reference was null.
     */
    fun <T> checkNotNull(reference: T?, errorMessage: String?): T {
        if (reference == null) {
            throw NullPointerException(errorMessage.toString())
        }
        return reference
    }
    // ------------------------------------------------------------------------
    //  Boolean Condition Checking (Argument)
    // ------------------------------------------------------------------------
    /**
     * Ensures that the given object reference is not null. Upon violation, a `NullPointerException` with the given message is thrown.
     *
     *
     * The error message is constructed from a template and an arguments array, after a similar
     * fashion as [String.format], but supporting only `%s` as a
     * placeholder.
     *
     * @param reference            The object reference
     * @param errorMessageTemplate The message template for the `NullPointerException` that is
     * thrown if the check fails. The template substitutes its `%s` placeholders with the
     * error message arguments.
     * @param errorMessageArgs     The arguments for the error message, to be inserted into the message
     * template for the `%s` placeholders.
     * @return The object reference itself (generically typed).
     * @throws NullPointerException Thrown, if the passed reference was null.
     */
    fun <T> checkNotNull(
        reference: T?,
        errorMessageTemplate: String?,
        vararg errorMessageArgs: Any?
    ): T {
        if (reference == null) {
            throw NullPointerException(format(errorMessageTemplate, *errorMessageArgs))
        }
        return reference
    }

    /**
     * Checks the given boolean condition, and throws an `IllegalArgumentException` if the
     * condition is not met (evaluates to `false`).
     *
     * @param condition The condition to check
     * @throws IllegalArgumentException Thrown, if the condition is violated.
     */
    fun checkArgument(condition: Boolean) {
        require(condition)
    }

    /**
     * Checks the given boolean condition, and throws an `IllegalArgumentException` if the
     * condition is not met (evaluates to `false`). The exception will have the given error
     * message.
     *
     * @param condition    The condition to check
     * @param errorMessage The message for the `IllegalArgumentException` that is thrown if
     * the check fails.
     * @throws IllegalArgumentException Thrown, if the condition is violated.
     */
    fun checkArgument(condition: Boolean, errorMessage: Any?) {
        require(condition) { errorMessage.toString() }
    }
    // ------------------------------------------------------------------------
    //  Boolean Condition Checking (State)
    // ------------------------------------------------------------------------
    /**
     * Checks the given boolean condition, and throws an `IllegalArgumentException` if the
     * condition is not met (evaluates to `false`).
     *
     * @param condition            The condition to check
     * @param errorMessageTemplate The message template for the `IllegalArgumentException`
     * that is thrown if the check fails. The template substitutes its `%s` placeholders
     * with the error message arguments.
     * @param errorMessageArgs     The arguments for the error message, to be inserted into the message
     * template for the `%s` placeholders.
     * @throws IllegalArgumentException Thrown, if the condition is violated.
     */
    fun checkArgument(
        condition: Boolean,
        errorMessageTemplate: String?,
        vararg errorMessageArgs: Any?
    ) {
        require(condition) { format(errorMessageTemplate, *errorMessageArgs) }
    }

    /**
     * Checks the given boolean condition, and throws an `IllegalStateException` if the
     * condition is not met (evaluates to `false`).
     *
     * @param condition The condition to check
     * @throws IllegalStateException Thrown, if the condition is violated.
     */
    fun checkState(condition: Boolean) {
        check(condition)
    }

    /**
     * Checks the given boolean condition, and throws an `IllegalStateException` if the
     * condition is not met (evaluates to `false`). The exception will have the given error
     * message.
     *
     * @param condition    The condition to check
     * @param errorMessage The message for the `IllegalStateException` that is thrown if the
     * check fails.
     * @throws IllegalStateException Thrown, if the condition is violated.
     */
    fun checkState(condition: Boolean, errorMessage: Any?) {
        check(condition) { errorMessage.toString() }
    }

    /**
     * Checks the given boolean condition, and throws an `IllegalStateException` if the
     * condition is not met (evaluates to `false`).
     *
     * @param condition            The condition to check
     * @param errorMessageTemplate The message template for the `IllegalStateException` that
     * is thrown if the check fails. The template substitutes its `%s` placeholders with
     * the error message arguments.
     * @param errorMessageArgs     The arguments for the error message, to be inserted into the message
     * template for the `%s` placeholders.
     * @throws IllegalStateException Thrown, if the condition is violated.
     */
    fun checkState(
        condition: Boolean,
        errorMessageTemplate: String?,
        vararg errorMessageArgs: Any?
    ) {
        check(condition) { format(errorMessageTemplate, *errorMessageArgs) }
    }

    /**
     * Ensures that the given index is valid for an array, list or string of the given size.
     *
     * @param index index to check
     * @param size  size of the array, list or string
     * @throws IllegalArgumentException  Thrown, if size is negative.
     * @throws IndexOutOfBoundsException Thrown, if the index negative or greater than or equal to
     * size
     */
    fun checkElementIndex(index: Int, size: Int) {
        checkArgument(size >= 0, "Size was negative.")
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
    }

    /**
     * Ensures that the given index is valid for an array, list or string of the given size.
     *
     * @param index        index to check
     * @param size         size of the array, list or string
     * @param errorMessage The message for the `IndexOutOfBoundsException` that is thrown if
     * the check fails.
     * @throws IllegalArgumentException  Thrown, if size is negative.
     * @throws IndexOutOfBoundsException Thrown, if the index negative or greater than or equal to
     * size
     */
    fun checkElementIndex(index: Int, size: Int, errorMessage: String?) {
        checkArgument(size >= 0, "Size was negative.")
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException(
                errorMessage.toString() + " Index: " + index + ", Size: " + size
            )
        }
    }
    // ------------------------------------------------------------------------
    //  Utilities
    // ------------------------------------------------------------------------
    /**
     * Ensures that future has completed normally.
     *
     * @throws IllegalStateException Thrown, if future has not completed or it has completed
     * exceptionally.
     */
    fun checkCompletedNormally(future: CompletableFuture<*>) {
        checkState(future.isDone)
        if (future.isCompletedExceptionally) {
            try {
                future.get()
            } catch (e: InterruptedException) {
                throw IllegalStateException(e)
            } catch (e: ExecutionException) {
                throw IllegalStateException(e)
            }
        }
    }
    // ------------------------------------------------------------------------
    /**
     * A simplified formatting method. Similar to [String.format], but with
     * lower overhead (only String parameters, no locale, no format validation).
     *
     *
     * This method is taken quasi verbatim from the Guava Preconditions class.
     */
    private fun format(template: String?, vararg args: Any?): String {
        var template = template
        val numArgs = args?.size ?: 0
        template = template.toString() // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        val builder = StringBuilder(template.length + 16 * numArgs)
        var templateStart = 0
        var i = 0
        while (i < numArgs) {
            val placeholderStart = template.indexOf("%s", templateStart)
            if (placeholderStart == -1) {
                break
            }
            builder.append(template.substring(templateStart, placeholderStart))
            builder.append(args[i++])
            templateStart = placeholderStart + 2
        }
        builder.append(template.substring(templateStart))

        // if we run out of placeholders, append the extra args in square braces
        if (i < numArgs) {
            builder.append(" [")
            builder.append(args[i++])
            while (i < numArgs) {
                builder.append(", ")
                builder.append(args[i++])
            }
            builder.append(']')
        }
        return builder.toString()
    }
}
