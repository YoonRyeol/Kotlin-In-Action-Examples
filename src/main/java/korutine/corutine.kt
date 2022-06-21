package korutine

import kotlinx.coroutines.*
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.stream.DoubleStream.generate
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun now() = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.MILLIS)

fun log(msg: String) = println("${now()}:${Thread.currentThread()}:${msg}")

fun launchGlobalScope() {
    GlobalScope.launch {
        log("coroutine started")
    }
}

fun runBlockingExample() {
    runBlocking {
        launch {
            log("GlobalScope.launch started.")
        }
    }
}

fun sumAll() {
    runBlocking {
        val d1 = async { delay(1000L); 1 }
        log("after async(d1)")
        val d2 = async { delay(1000L); 2 }
        log("after async(d2)")
        val d3 = async { delay(1000L); 3 }
        log("after async(d3)")
    }
}

fun yieldExample() {
    runBlocking {
        launch {
            log("1")
            yield()
            log("3")
            yield()
            log("5")
        }
        log("after first launch")
        launch {
            log("2")
            yield()
            log("4")
            yield()
            log("6")
        }
        log("after second launch")
    }
}

suspend fun yieldThreeTimes() {
    log("1")
    delay(1000L)
    yield()
}



fun main(){
}
