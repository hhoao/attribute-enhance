package org.hhoa.aten.utils

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * FileUtils.
 *
 * @author hhoa
 * @since 2023/6/12
 **/

object FileUtils {
    private fun copyJarEntry(jar: JarFile, entry: JarEntry, dest: File) {
        dest.parentFile.mkdirs()
        val outputStream = FileOutputStream(dest)
        val inputStream = jar.getInputStream(entry)
        try {
            val buffer = ByteArray(8192)
            var len: Int
            while ((inputStream.read(buffer).also { len = it }) > 0) {
                outputStream.write(buffer, 0, len)
                outputStream.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Copies a directory or file from a jar file to an external directory.
     */
    fun copyJarResourcesToDirectory(
        fromJarFile: File,
        destDir: String,
        fileName: String,
        option: CopyOption = StandardCopyOption.COPY_ATTRIBUTES,
    ) {
        val jar = JarFile(fromJarFile)
        val entries = jar.entries()
        File(destDir).mkdirs()
        var copyAll = false
        var newFileName: String = fileName
        if (fileName.endsWith("/*")) {
            copyAll = true
            newFileName = fileName.substring(0, fileName.length - "/*".length)
        }
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            if (entry.isDirectory) {
                continue
            }
            if ((!copyAll && entry.name.equals(newFileName)) || entry.name.startsWith("$newFileName/")) {
                val commonLength = if (copyAll) newFileName.length else newFileName.substringBeforeLast('/').length
                val substring =
                    entry.name.substring(commonLength)
                val dest = File("$destDir/$substring")
                if (!dest.exists() || option == StandardCopyOption.REPLACE_EXISTING) {
                    copyJarEntry(jar, entry, dest)
                }
            }
        }
    }

    /**
     * create or get exists dir.
     */
    fun getOrCreateExistDir(file: File): File {
        file.mkdirs()
        return file
    }

    /**
     * Copy.
     *
     * @param srcFile source file
     * @param destFile destination file
     */
    fun copy(srcFile: File, destFile: File) {
        val addSuffix: (File) -> String = { path: File ->
            if (path.absolutePath.endsWith("/")) {
                path.absolutePath
            } else path.absolutePath + "/"
        }
        if (srcFile.isFile) {
            Files.copy(srcFile.toPath(), File(destFile, srcFile.name.substringAfterLast('/')).toPath())
            return
        }

        val newFile = File(addSuffix(destFile), srcFile.absolutePath.substringAfterLast('/'))
        newFile.mkdirs()
        val files = srcFile.listFiles()
        for (file in files!!) {
            copy(file, newFile)
        }
    }
}
