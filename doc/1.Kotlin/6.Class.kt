222//
// class
//      class Empty
// primary constructor
//      class Person constructor(firstName: String)
//      class Person(firstName: String)
//      class Person(val name: String) {
//          val children: MutableList<Person> = mutableListOf()
//          constructor(name: String, parent: Person) : this(name) {
//              parent.children.add(this)
//          }
//          init {
//              println("First initializer block that prints $name")
//          }
//      }
// secodary constructor
//      class Pet {
//          constructor(owner: Person) {
//              owner.pets.add(this) // adds this pet to the list of its owner's pets
//          }
//      }
// properties
//      class Rectangle(val width: Int, val height: Int) {
//          lateinit val area: Int
//              get() = this.width * this.height
//              set(value) {
//                  setDataFromString(value) // parses the string and assigns values to other properties
//              }
//      }
// interface
//      interface MyInterface {
//          fun bar()
//      }
// Single Abstract Method SAM
//      fun interface KRunnable {
//          fun invoke()
//      }
// data class
//     data class User(val name: String, val age: Int)
// enum
//      enum class Color(val rgb: Int) {
//          RED(0xFF0000),
//          GREEN(0x00FF00)
//      }
//      enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
//          PLUS {
//              override fun apply(t: Int, u: Int): Int = t + u
//          },
//          TIMES {
//              override fun apply(t: Int, u: Int): Int = t * u
//          };
//          override fun applyAsInt(t: Int, u: Int) = apply(t, u)
//      }
