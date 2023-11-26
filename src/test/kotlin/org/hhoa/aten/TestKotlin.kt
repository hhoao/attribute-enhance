package org.hhoa.aten

import org.junit.jupiter.api.Test
import java.text.MessageFormat
import java.util.regex.Pattern

/**
 * TestKotlin
 *
 * @author hhoa
 * @since 2023/6/13
 **/

class TestKotlin {
    @Test
    fun test() {
        val reg = "\\s*{0}:?\\s*{1}\\s*"
        val value = "([+-]?\\d+[- ]*[+-]?\\d*)";
        val format = MessageFormat.format(reg, "attack", value)
        val str = "攻击力 attack: 10 - 20"
        val compile = Pattern.compile(format)
        val matcher = compile.matcher(str)
        val matches = matcher.find()
        if (matches) {
            println(matcher.group(1))
        }
    }
}
