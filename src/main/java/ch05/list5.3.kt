package ch05

data class Person(val name: String, val age: Int)

fun main() {
    println(alphabet())
}

fun alphabet() = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
    toString()

}.toString()
