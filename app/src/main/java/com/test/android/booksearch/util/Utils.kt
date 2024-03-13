package com.test.android.booksearch.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.android.booksearch.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException
import kotlinx.coroutines.delay


fun CallErrors.getMessage(context: Context): String {
    return when (this) {
        is CallErrors.ErrorEmptyData -> context.getString(R.string.error_empty_data)
        is CallErrors.ErrorServer -> context.getString(R.string.error_server_error)
        is CallErrors.ErrorException -> context.getString(
            R.string.error_exception
        )
    }
}

fun Boolean.runIfTrue(block: () -> Unit) {
    if (this) {
        block()
    }
}

const val MAX_RETRIES = 3L
private const val INITIAL_BACKOFF = 2000L

fun getBackoffDelay(attempt: Long) = INITIAL_BACKOFF * (attempt + 1)

fun <T : Any> Flow<Result<T>>.applyCommonSideEffects() = retryWhen { cause, attempt ->
    when {
        (cause is IOException && attempt < MAX_RETRIES) -> {
            delay(getBackoffDelay(attempt))
            true
        }

        else -> {
            false
        }
    }
}.onStart {
    emit(Result.Loading)
}.catch {
    emit(Result.Error(CallErrors.ErrorException(it)))
}