//
// _ 未引用对象
// $ 文本模板
// .. Range表达式
//      begin .. end -> [begin, end]
//      begin .. until end -> [begin, end)
//      begin .. end step stepN -> begin, begin + stepN, ...
//      begin .. downTo end -> [end, begin]
// :: 方法引用、类引用
//      lambda方法引用
//      this::method简写
// !!: 保证非空
// is 类型判断
// as 类型强转
// as? 类型强转, nullable
// it lambda表达式唯一参数隐式名称
// null
//      ?. if-not-null          println(file?.size)
//      ?. ?: if-not-null-else  println(file?.size ?: "empty")
//      ?: if-null              println(file ?: "null")
//      value?.let {} ?: default execute if not null else default
// object:
//      匿名类
//          val helloWorld = object {
//              val hello = "Hello"
//              val World = "World"
//              override fun toString() = "$hello $world"
//          }
//      单例
//          object DataProviderManager {
//              fun registerDataProvider(provider: DataProvider) {
//              }
//              val allDataProviders: Collection<DataProvider>
//                  get() = // ...
//          }