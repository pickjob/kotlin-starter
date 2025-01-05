// interface SendChannel<in E>
//      val onSend: SelectClause2<E, SendChannel<E>>
//      fun trySend(element: E): ChannelResult<Unit>
//      fun close(cause: Throwable? = null): Boolean
//      suspend fun send(element: E)
//
//      fun SendChannel<E>.trySendBlocking(element: E): ChannelResult<Unit>
// interface ReceiveChannel<out E>
//      val isEmpty: Boolean
//      val onReceive: SelectClause1<E>
//      tryReceive(): ChannelResult<E>
//      fun cancel(cause: CancellationException? = null)
//      suspend fun receive(): E
//      suspend fun receiveCatching(): ChannelResult<E>
// interface Channel<E> : SendChannel<E> , ReceiveChannel<E>
//      fun Channel(
//          capacity: Int = RENDEZVOUS,
//          onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND,
//          onUndeliveredElement: (E) -> Unit? = null
//      ): Channel<E>
// ChannelResult
//      fun ChannelResult<T>.onSuccess(action: (value: T) -> Unit): ChannelResult<T>
//      fun ChannelResult<T>.onClosed(action: (exception: Throwable?) -> Unit): ChannelResult<T>
//      fun ChannelResult<T>.onFailure(action: (exception: Throwable?) -> Unit): ChannelResult<T>