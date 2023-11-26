package org.hhoa.aten.utils

import java.util.regex.Pattern
import kotlin.random.Random

/**
 * StringUtils.
 *
 * @author hhoa
 * @since 2023/6/18
 **/

object StringUtils {
    /**
     * Default number range compile.
     * to pattern like range case "10 - 20", "-10 - 20", "+10 - +20" .etc.
     */
    val defaultNumberRangeCompile: Pattern = Pattern.compile("^([+-]?\\d+)\\s*(?:-\\s*([+-]?\\d+)\$)?")

    /**
     * use defaultNumberRangeCompile to Parse range number.
     *
     * @param str str like number range "10 - 20", "-10 - 20", "+10 - +20" .etc
     * @return number
     */
    @Throws(NumberFormatException::class)
    @JvmStatic
    fun parseNumber(str: String): Double {
        val matcher = defaultNumberRangeCompile.matcher(str)
        if (matcher.matches()) {
            val number1 = matcher.group(1)
            val number2 = matcher.group(2)
            return if (number2 == null) {
                number1.toDouble()
            } else {
                Random.nextDouble(number1.toDouble(), number2.toDouble())
            }
        }
        throw NumberFormatException("parseNum exception")
    }
}
