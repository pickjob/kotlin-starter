//
// CoroutineScope: 协程
//      Constructor:
//          fun CoroutineScope(context: CoroutineContext): CoroutineScope
//          fun MainScope(): CoroutineScope = ContextScope(SupervisorJob() + Dispatchers.Main)
//      Builder:
//          runBlocking(
//              context: CoroutineContext = EmptyCoroutineContext,
//              block: suspend CoroutineScope.() -> T
//          ): T 普通函数, 阻塞当前线程, 不可管理协程
//          suspend coroutineScope(
//              block: suspend CoroutineScope.() -> R
//          ): R suspen函数, 不阻塞当前线程, 失败后停止子协程
//          suspend supervisorScope(
//              block: suspend CoroutineScope.() -> R
//          ): R suspen函数, 不阻塞当前线程, 失败后不影响子协程
//          suspend withContext(
//              context: CoroutineContext,
//              block: suspend CoroutineScope.() -> T
//          ): T suspen函数, + NonCancellable 不可取消
//          suspend withTimeout(
//              timeMillis: Long,
//              block: suspend CoroutineScope.() -> T
//          ): T 超时抛异常 TimeoutCancellationException
//          suspend withTimeout(
//              timeout: Duration,
//              block: suspend CoroutineScope.() -> T
//          ): T 超时抛异常 TimeoutCancellationException
//          suspend withTimeoutOrNull(
//              timeMillis: Long,
//              block: suspend CoroutineScope.() -> T
//          ): T? 超时返回空
//          CoroutineScope.launch(
//              context: CoroutineContext = EmptyCoroutineContext,
//              start: CoroutineStart = CoroutineStart.DEFAULT,
//              block: suspend CoroutineScope.() -> Unit
//          ): Job 普通函数, 不阻塞当前线程, Job可管理协程
//          CoroutineScope.async(
//              context: CoroutineContext = EmptyCoroutineContext,
//              start: CoroutineStart = CoroutineStart.DEFAULT,
//              block: suspend CoroutineScope.() -> T
//          ): Deferred<T> 不阻塞当前线程, Defer可管理协程
//          CoroutineScope.produce(
//              context: CoroutineContext = EmptyCoroutineContext,
//              capacity: Int = 0,
//              block: suspend ProducerScope<E>.() -> Unit
//          ): ReceiveChannel<E>
//
//          fun CoroutineScope.cancel(cause: CancellationException? = null): 取消
//          fun CoroutineScope.cancel(message: String, cause: Throwable? = null): 取消
//
//          operator fun CoroutineScope.plus(context: CoroutineContext): CoroutineScope
// CoroutineDispatcher: CoroutineContext实现, 协程调度
//      fun newSingleThreadContext(name: String): CloseableCoroutineDispatcher
//      fun newFixedThreadPoolContext(nThreads: Int, name: String): CloseableCoroutineDispatcher
//
//      fun CoroutineContext.cancel(cause: CancellationException? = null): 取消
//
//      Dispatchers.Default: 默认
//      Dispatchers.IO: IO线程池
//      Dispatchers.Main: 主线程
//      Dispatchers.Unconfined
// Job(): CoroutineContext实现, 失败后停止子协程
//      fun Job.cancel(message: String, cause: Throwable? = null): 取消
//      suspend fun Job.join()
//      suspend fun Job.cancelAndJoin()
// SupervisorJob(): CoroutineContext实现, 失败后不影响子协程
// data class CoroutineName(val name: String): CoroutineContext实现, Coroutine名称
// CoroutineExceptionHandler: CoroutineContext实现, 异常处理
//      fun handleException(context: CoroutineContext, exception: Throwable)
// Suspend function:
//      suspend fun currentCoroutineContext(): CoroutineContext 返回当前 CoroutineContext
//      suspend fun yield(): 释放时间片
//      suspend fun delay(timeMillis: Long): 延迟
//      suspend fun delay(duration: Duration): 延迟
//      suspend fun <T> awaitAll(vararg deferreds: Deferred<T>): List<T>