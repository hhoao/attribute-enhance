package org.hhoa.aten.scripts

import com.google.inject.Singleton
import groovy.lang.Binding
import groovy.util.GroovyScriptEngine
import groovy.util.ResourceException
import groovy.util.ScriptException
import org.hhoa.aten.utils.LoggerFactory
import java.io.File

/**
 * GroovyManager.
 *
 * @author hhoa
 * @since 2023/6/12
 **/

@Singleton
class GroovyManager {
    private val logger = LoggerFactory.getLogger(GroovyManager::class.java)
    private val defaultBinding = Binding()

    private val defaultEngine: GroovyScriptEngine = run {
        val groovyScriptEngine = GroovyScriptEngine("", this.javaClass.classLoader)
        groovyScriptEngine.groovyClassLoader.addClasspath(this.javaClass.protectionDomain.codeSource.location.path)
        groovyScriptEngine
    }

    @Throws(ScriptException::class, ResourceException::class, Exception::class)
    fun loadScript(file: File): Class<in Nothing> {
        return defaultEngine.loadScriptByName(file.absolutePath)
    }
}
