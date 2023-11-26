package org.hhoa.aten

import org.junit.jupiter.api.Test
import java.util.regex.Pattern.compile

/**
 * TestPattern
 *
 * @author hhoa
 * @since 2023/6/17
 **/

class TestPattern {
    @Test
    fun test() {
//        val str = "攻击力:. 16"
//        val compile = compile("\\s*\\w|[\u4E00-\u9FFF]+:.\\s*(\\d+)\\s*")
//        val matcher = compile.matcher(str)
//        if (matcher.matches()) {
//            matcher.group(1)
//        }
        val str = "-20"
        val compile = compile("^([+-]?\\d+)\\s*(?:-\\s*([+-]?\\d+)$)?")
        var matcher = compile.matcher(str)
        if (matcher.matches()) {
            println(matcher.group(1))
            println(matcher.group(2))
        }
    }
}
