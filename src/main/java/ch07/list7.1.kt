package ch07

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.time.LocalDate
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

data class Point(val x: Int, val y: Int)

operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y+ other.y)
}

operator fun Point.times(scale: Double): Point {
    return Point((x*scale).toInt(), (y*scale).toInt())
}

operator fun Double.times(other: Point) : Point{
    return Point((other.x*this).toInt(), (other.y*this).toInt())
}

operator fun Point.unaryMinus(): Point {
    return Point(-x, -y)
}

//class Person(
//    val firstName: String, val lastName: String
//): Comparable<Person>{
//    override fun compareTo(other: Person): Int {
//        return compareValuesBy(this, other, Person::lastName, Person::firstName)
//    }
//}

operator fun Point.get(index: Int) : Int {
    return when(index) {
        0 -> x
        1 -> y
        else -> throw java.lang.IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

data class MutablePoint(var x: Int, var y: Int)

operator fun MutablePoint.set(index: Int, value: Int){
    when(index){
        0 -> x = value
        1 -> y = value
        else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
    return p.x in upperLeft.x until lowerRight.x &&
            p.y in upperLeft.y until lowerRight.y
}

operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
    object : Iterator<LocalDate> {
        var current = start

        override fun hasNext(): Boolean = current <= endInclusive

        override fun next() = current.apply { current = plusDays(1) }

    }

data class NameComponenents(val name: String, val extension: String)

fun splitFilename(fullName: String): NameComponenents {
    val (name, extension) = fullName.split('.', limit=2)
    return NameComponenents(name, extension)
}

fun printEntries(map: Map<String, String>) {
    for((key, value) in map){
        println("$key -> $value")
    }
}


class ObservableProperty(
    var propValue: Int, val changeSupport: PropertyChangeSupport
){
    operator fun getValue(p: Person, prop: KProperty<*>): Int = propValue
    operator fun setValue(p: Person, prop: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
}

open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}

class Person(
    val name: String, age: Int, salary: Int
): PropertyChangeAware() {
    private val observer = {
        prop: KProperty<*>, oldValue:Int, newValue: Int -> changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
    var age: Int by Delegates.observable(age, observer)
    var salary: Int by Delegates.observable(salary, observer)
}



fun main(){
    val p = Person("Dmitry", 34, 2000)
    p.addPropertyChangeListener(
        PropertyChangeListener { event ->
            println("Property ${event.propertyName} changed " +
                    "from ${event.oldValue} to ${event.newValue}")
        }
    )
    p.age = 35
    p.salary = 2100
}
