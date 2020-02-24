package io.arabic.dictionary.manager.utils.ext

import kotlinx.coroutines.*
import kotlin.coroutines.resumeWithException

suspend fun <T> io(block: suspend () -> T): T {
    return withContext(Dispatchers.IO) {
        block()
    }
}

suspend fun <T> io(job: Job, block: suspend () -> T): T {
    return withContext(job + Dispatchers.IO) {
        block()
    }
}

suspend fun <T> main(block: suspend () -> T): T {
    return withContext(Dispatchers.Main) {
        block()
    }
}

fun <T> CancellableContinuation<T>.safeResume(value: T, exception: Throwable? = null) {
    if (isActive) {
        if (exception == null) {
            resumeWith(Result.success(value))
        } else {
            resumeWithException(exception)
        }
    }
}

fun ui(job: Job? = null): CoroutineScope {
    return CoroutineScope(if (job == null) Dispatchers.Main else job + Dispatchers.Main)
}

val ui: CoroutineScope get() = ui()

inline fun <T, R> T.safe(block: T.() -> R): R? {
    return try {
        block()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

fun <T> T?.toStringOrEmpty(): String = this?.toString() ?: ""

fun StringBuilder.whiteSpace(): StringBuilder {
    return append(" ")
}

fun java.lang.StringBuilder.lineBreak(): StringBuilder {
    return append("\n")
}

fun StringBuilder.appendIfNotNull(value: Any?): StringBuilder {
    if (value != null) {
        append(value.toString())
    }
    return this
}