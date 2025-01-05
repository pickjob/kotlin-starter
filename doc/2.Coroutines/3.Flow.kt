//
// 几个重要接口、类
//      interface Flow<out T>: 冷流, 必须terminal函数实际调用
//          suspend fun collect(collector: FlowCollector<T>)
//      interface SharedFlow<out T> : Flow<T> 热流, 无订阅可执行
//      interface StateFlow<out T> : SharedFlow<T> 热流, 无订阅可执行, 可读, 用户状态属性
//
//      interface FlowCollector<in T>
//          suspend fun emit(value: T)
// 构造函数:
//      fun flow(block: suspend FlowCollector<T>.() -> Unit): Flow<T>
// 转换函数:
//      fun Flow<T>.shareIn(
//          scope: CoroutineScope,
//          started: SharingStarted,
//          replay: Int = 0
//      ): SharedFlow<T>
//      fun Flow<T>.stateIn(
//          scope: CoroutineScope,
//          started: SharingStarted,
//          initialValue: T
//      ): StateFlow<T>
// 合并流:
//      fun Flow<T1>.zip(other: Flow<T2>, transform: suspend (T1, T2) -> R): Flow<R>
//      fun Flow<T1>.combine(flow: Flow<T2>, transform: suspend (a: T1, b: T2) -> R): Flow<R>
//      fun combine(flow: Flow<T1>, flow2: Flow<T2>, transform: suspend (a: T1, b: T2) -> R): Flow<R>
//      fun Flow<T>.flatMapConcat(transform: suspend (value: T) -> Flow<R>): Flow<R>
//      fun Flow<Flow<T>>.flattenConcat(): Flow<T>
//      fun Flow<T>.flatMapMerge(concurrency: Int = DEFAULT_CONCURRENCY, transform: suspend (value: T) -> Flow<R>): Flow<R>
//      fun Flow<Flow<T>>.flattenMerge(concurrency: Int = DEFAULT_CONCURRENCY): Flow<T>
//      fun Flow<T>.flatMapLatest(crossinline transform: suspend (value: T) -> Flow<R>): Flow<R>
// 特殊操作:
//      fun Flow<T>.flowOn(context: CoroutineContext): Flow<T> 切换前面操作线程
// 异常处理:
//      fun Flow<T>.catch(action: suspend FlowCollector<T>.(cause: Throwable) -> Unit): Flow<T>
//      fun Flow<T>.retry(retries: Long = Long.MAX_VALUE, predicate: suspend (cause: Throwable) -> Boolean = { true } ): Flow<T>
//      fun Flow<T>.retryWhen(predicate: suspend FlowCollector<T>.(cause: Throwable, attempt: Long) -> Boolean): Flow<T>
// 中间操作:
//      fun Flow<T>.debounce(timeoutMillis: Long): Flow<T> 防抖
//      fun Flow<T>.debounce(timeoutMillis: (T) -> Long): Flow<T> 防抖
//      fun Flow<T>.debounce(timeout: Duration): Flow<T>  防抖
//      fun Flow<T>.buffer(capacity: Int = BUFFERED, onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND): Flow<T> 缓存
//      fun Flow<T>.conflate(): Flow<T> 只接收最新数据, 等价于 buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
//      fun Flow<T>.take(count: Int): Flow<T> 取前N个
//      fun Flow<T>.takeWhile(predicate: suspend (T) -> Boolean): Flow<T>
//      fun Flow<T>.chunked(size: Int): Flow<List<T>> 分割小块
//      fun Flow<T>.sample(periodMillis: Long): Flow<T> 取样
//      fun Flow<T>.sample(period: Duration): Flow<T>
//      fun Flow<T>.drop(count: Int): Flow<T> 丢弃
//      fun Flow<T>.dropWhile(predicate: suspend (T) -> Boolean): Flow<T>  丢弃
//      fun Flow<T>.filter(crossinline predicate: suspend (T) -> Boolean): Flow<T> 过滤
//      fun Flow<T>.filterNot(crossinline predicate: suspend (T) -> Boolean): Flow<T> 过滤
//      fun Flow<T?>.filterNotNull(): Flow<T> 过滤
//      fun Flow<T>.map(crossinline transform: suspend (value: T) -> R): Flow<R>
//      fun Flow<T>.mapLatest(transform: suspend (value: T) -> R): Flow<R>
//      fun Flow<T>.mapNotNull(crossinline transform: suspend (value: T) -> R?): Flow<R>
//      fun Flow<T>.transform(crossinline transform: suspend FlowCollector<R>.(value: T) -> Unit): Flow<R>
//      fun Flow<T>.transformLatest(transform: suspend FlowCollector<R>.(value: T) -> Unit): Flow<R>
//      fun Flow<T>.transformWhile(transform: suspend FlowCollector<R>.(value: T) -> Boolean): Flow<R>
//      fun <T> Flow<T>.withIndex(): Flow<IndexedValue<T>>
//      fun Flow<T>.timeout(timeout: Duration): Flow<T>
//      fun Flow<T>.onEach(action: suspend (T) -> Unit): Flow<T>
//      fun Flow<T>.onStart(action: suspend FlowCollector<T>.() -> Unit): Flow<T>
//      fun Flow<T>.onCompletion(action: suspend FlowCollector<T>.(cause: Throwable?) -> Unit): Flow<T>
// Terminal操作:
//      suspend fun Flow<*>.collect()
//      suspend fun Flow<T>.collectIndexed(crossinline action: suspend (index: Int, value: T) -> Unit)
//      suspend fun Flow<T>.collectLatest(action: suspend (value: T) -> Unit): 接收最新, 如果新的出现, 老的取消
//      suspend fun Flow<T>.toSet(destination: MutableSet<T> = LinkedHashSet()): Set<T>
//      suspend fun Flow<T>.toList(destination: MutableList<T> = ArrayList()): List<T>
//
//      suspend fun Flow<T>.single(): T
//      suspend fun Flow<T>.singleOrNull(): T?
//      suspend fun Flow<T>.first(): T
//      suspend fun Flow<T>.first(predicate: suspend (T) -> Boolean): T
//      suspend fun Flow<T>.firstOrNull(): T?
//      suspend fun Flow<T>.firstOrNull(predicate: suspend (T) -> Boolean): T?
//      suspend fun Flow<T>.last(): T
//      suspend fun Flow<T>.lastOrNull(): T?
//      suspend fun Flow<T>.count(): Int
//      suspend fun Flow<T>.count(predicate: suspend (T) -> Boolean): Int
//
//      suspend fun Flow<T>.reduce(operation: suspend (accumulator: S, value: T) -> S): S
//      suspend fun Flow<T>.fold(initial: R, crossinline operation: suspend (acc: R, value: T) -> R): R
//
//      fun Flow<T>.launchIn(scope: CoroutineScope): Job
//      fun Flow<T>.produceIn(scope: CoroutineScope): ReceiveChannel<T>