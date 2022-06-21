package ch11

fun buildString(
    builderAction: StringBuilder.() -> Unit
): String {
    val sb = StringBuilder()
    sb.builderAction()
    return sb.toString()
}


fun main(){
    val appendExcl : StringBuilder.() -> Unit =
        { this.append("!") }
    val stringBuilder = StringBuilder("Hi!")
    stringBuilder.appendExcl()
    println(stringBuilder)
    println(buildString(appendExcl))
}
