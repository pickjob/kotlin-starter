//
// 作用域函数: 在一个对象上执行一个代码块
//      区别:
//          对象引用: this it
//          返回值
// let: it引用, 返回lambda表达结果, 仅使用非空值执行代码块
//      fun <T, R> T.let(block: (T) -> R): R
//          str?.let {
//              println("let() called on $it")
//              it.length
//          }
// with: this引用, 返回lambda表达结果, 对于这个对象，执行以下操作
//      fun <T, R> with(receiver: T, block: T.() -> R): R
//          with(configuration) {
//              println("$host:$port")
//          }
// apply: this引用, 返回上下文, 将以下赋值操作应用于对
//      fun <T> T.apply(block: T.() -> Unit): T
//          jake.apply {
//              name = "Jake"
//              age = 30
//          }.toString()
// also: it引用, 返回上下文, 并且用该对象执行以下操作
//      fun <T> T.also(block: (T) -> Unit): T
//          Person("Jake", 30, "Android developer").also {
//              writeCreationLog(it)
//          }
// run: this引用, 返回lambda表达结果, 同时包含对象初始化和返回值
//      fun <T, R> T.run(block: T.() -> R): R
//          service.run {
//              port = 8080
//              query(it.prepareRequest() + " to port ${it.port}")
//          }
// use: 自动释放资源
//      FileWriter("test.txt").use { w -> w.write("something") }
