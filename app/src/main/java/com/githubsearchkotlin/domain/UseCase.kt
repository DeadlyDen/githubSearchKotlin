package com.githubsearchkotlin.domain

import android.util.Log
import kotlinx.coroutines.experimental.*
import kotlin.coroutines.experimental.CoroutineContext

abstract class UseCase<T> {

    protected var parentJob: Job = Job()

    var backgroundContext: CoroutineContext = Dispatchers.Default
    var foregroundContext: CoroutineContext = Dispatchers.Main


    abstract suspend fun executeOnBackground(): T

    fun execute(onComplete: (T) -> Unit, onError: (Throwable) -> Unit) {
        parentJob.cancel()
        parentJob = Job()
        GlobalScope.launch(foregroundContext) {
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground()
                }
                onComplete.invoke(result)
            } catch (e: CancellationException) {
                Log.d("UseCaseGithub", "canceled by user")
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    protected suspend fun <X> background(context: CoroutineContext = backgroundContext, block: suspend () -> X): Deferred<X> {
        return GlobalScope.async(context) {
            block.invoke()
        }
    }

    fun unsubscribe() {
        parentJob.cancel()
    }

}