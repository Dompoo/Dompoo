package com.lannstark.lec12

/**
 * static
 */
class Person private constructor (
	private val name: String,
	private val age: Int
) {
	// 동행 객체, static과 같다.
	// 단, 동행 객체도 객체로 간주하고, 이름을 붙일 수 있다.
	companion object Factory {
		private const val MIN_AGE = 0 // 찐 상수에는 const를 적는다.
		@JvmStatic
		fun newBaby(name: String):Person {
			return Person(name, MIN_AGE)
		}
	}
}

fun main1() {
	val newBaby = Person.newBaby("hello")
	//Java에서 이처럼 바로 사용하기 위해서는 @JvmStatic을 붙여야 한다.
}

/**
 * 싱글톤 객체는 object 키워드를 통해 만든다.
  */

object Singleton {
	var a: String = "hello"
}

fun main2() {
	println(Singleton.a)
}

/**
 * 익명 클래스
 */
private fun moveSomeThing(movable: Movable) {
	movable.move()
	movable.fly()
}

fun main() {
	moveSomeThing(
		object : Movable {
			override fun move() {
				println("무빗무빗")
			}

			override fun fly() {
				println("푸닥푸닥")
			}

		}
	)

}