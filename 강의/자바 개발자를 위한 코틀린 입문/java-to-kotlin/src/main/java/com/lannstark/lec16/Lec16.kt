package com.lannstark.lec16

/**
 * 확장 함수
 *
 * 자바의 기존 객체에, 추가 기능을 코틀린으로 작성하고 싶을 경우 사용
 * 시그니처는 기존 객체에 있는 것처럼, 기능은 외부 코틀린에 작성된다.
 * 캡슐화를 위해 기존 객체의 private, protected 멤버에는 접근하지 못한다.
 *
 * 자바에서 사용하고 싶다면, static 메서드 처럼 사용하면 된다.
 *
 * 확장 프로퍼티도 있는데, 기본적인 모양은 똑같이 사용하면 된다.
 */
fun String.getLastChat() : Char {
	return this[this.length - 1]
}

// 확장 함수가 기존 멤버 함수와 시그니처가 같다면 멤버 함수가 우선적으로 호출된다.
fun Person.nextYearAge(): Int {
	println("확장 함수")
	return this.age + 1;
}


fun main1() {
	val person = Person("hello", "world", 23)
	person.nextYearAge()
}

/**
 * 중위 함수(infix)
 *
 * 변수.함수(argument) 와
 * 변수 함수 argument로도 호출되는 함수
 * downTo, step 등도 모두 사실 중위함수였다.
 */
infix fun Int.add(arg: Int): Int {
	return this + arg
}
fun main() {
	val num1 = 3
	println(7.add(3))
	println(7 add 3)
}

/**
 * inline 함수
 *
 * 함수의 로직 자체가 컴파일시에 아예 복붙된다.
 * 런타임시에 stack에 쌓이는게 아니라, 그냥 박혀들어가기 때문에 성능은 좋으나, 신중하게 사용 필요하다.
 * 굳이?
 */

/**
 * 지역 함수
 *
 * 함수 안에 함수를 선언
 * 함수 안에서 중복되는 부분을 추출, 지역 함수로 선언하여 사용한다.
 * 굳이?
 */