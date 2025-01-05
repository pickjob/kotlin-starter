// Unidirectional Data Flow(UDF): 单向数据流
//      state flows down / events flow up: 状态向下, 事件向上
//      Event: Part of the UI generates an event and passes it upward
//      Update state: An event handler might change the state
//      Display state: The state holder passes down the state, and the UI displays it
// State hoisting: 状态提升
//      value: T: the current value to display
//      onValueChange: (T) -> Unit
// Recomposition:
//      重组场景
//          The function has a non-Unit return type
//          The function is annotated with @NonRestartableComposable or @NonSkippableComposable
//          A required parameter is of a non-stable type
//          There is an experimental compiler mode, Strong Skipping, which relaxes the last requirement.
//      非重组场景
//          @Stable
//          Boolean, Int, Long, Float, Char, Strings
//          All function types (lambdas)
// State
//      remember / rememberSaveable: 记住状态, 变化重组
//          var value by remember { mutableStateOf(default) }
//          val mutableState = remember { mutableStateOf(default) }
//          val (value, setValue) = remember { mutableStateOf(default) }
//      State
//          mutableXXStateOf(value: Int): MutableXXState
//              XX: Int Long Float Double
//          mutableStateOf(value: T, policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()): MutableState<T>
//          mutableStateListOf(): SnapshotStateList<T>
//          mutableStateListOf(vararg elements: T): SnapshotStateList<T>
//          mutableStateMapOf(): SnapshotStateMap<K, V>
//          mutableStateMapOf(vararg pairs: Pair<K, V>): SnapshotStateMap<K, V>
// Side Effect: 副作用函数, 影响外部状态
//      LaunchedEffect：启动调用
//      DisposableEffect：销毁时调用
//      SideEffect：每次 Recomposition 调用
//
//      rememberCoroutineScope：获取组合感知作用域，以在可组合项外启动协程(可组合函数)
//      rememberUpdatedState：在效应中引用值，在值发生更改时不应重启
//      produceState：将非 Compose 状态转换为 Compose 状态
//      derivedStateOf：将一个或多个状态对象转换为另一种状态
//      snapshotFlow：将 Compose 的 State 转换为 Flow
// CompositionLocal + CompositionLocalProvider: 隐式向下传递数据
//      compositionLocalOf / staticCompositionLocalOf
//      CompositionLocalProvider(LocalXXX provides variable) {}
//      预定义CompositionLocal:
//          LocalLifecycleOwner
//          LocalViewModelStoreOwner