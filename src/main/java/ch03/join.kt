package ch03

import java.lang.StringBuilder

const val UNIX_LINE_SEPARATOR = '\n'


fun <T> Collection<T>.joinToString(
    separator: String = ",",
    prefix: String = "",
    postfix: String = ""
): String
{
    val result = StringBuilder(prefix)

    for((index, element) in this.withIndex()){
        if(index > 0) result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}

val String.lastChar: Char
    get() = get(length-1)

var StringBuilder.lastChar: Char
    get() = get(length-1)
    set(value: Char){
        this.setCharAt(length-1, value)
    }

fun parsePath(path: String){
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if(matchResult != null){
        val (directory, filename, extension) = matchResult.destructured
        println("Dir: $directory, name: $filename, ext: $extension")
    }
}

val string = "won"
val price = """$string 99.9"""
