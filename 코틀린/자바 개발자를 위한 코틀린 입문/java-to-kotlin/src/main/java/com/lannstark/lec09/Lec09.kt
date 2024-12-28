package com.lannstark.lec09

public class Person1 constructor(name: String, age: Int) {
	val name: String = name
	var age: Int = age
	// 코틀린에서는 필드만 만들면 getter setter를 자동으로 만들어준다.
	// 이것을 프로퍼티 선언이라고 부른다.
}

// 기본으로 public이므로 생략 가능하다.
// constructor 지시어도 생략 가능하다.
// ->

class Person2(name: String, age: Int) {
	val name: String = name
	var age: Int = age
}

// 프로퍼티 선언을 생성자와 합칠 수 있다.
// ->

class Person3(
	val name: String,
	var age: Int
) {
}

// Body가 없다면 생략할 수 있다.
// ->
class Person4(
	val name: String,
	var age: Int
)

fun main1() {
	//getter setter는 getName()이 아니라 바로 접근하는 것처럼 불려진다.
	val person = Person4("dompoo", 25)
	person.age = 24;
	println("이름은 ${person.name}이고, 나이는 ${person.age}입니다.")

	//java객체 getter setter 또한 같은 방식으로 호출할 수 있다.
	val javaPerson = JavaPerson("dompoo", 25)
	javaPerson.age = 24;
}

/**
 * 생성자
 * 가장 위의 생성자는 주생성자라고 불린다. 이는 반드시 존재해야 한다.
 * 그 아래의 constructor를 통해 부생성자를 만들 수 있다. 이는 반드시 최종적으로 주생성자를 호출해야 한다.
 */
class Person5(
	val name: String,
	var age: Int
) {
	// 생성자가 호출되는 시점에 사용된다.
	// 생성자 파라미터 검증등을 진행할 수 있다.
	init {
		if (age <= 0) {
			throw IllegalArgumentException("나이는 ${age}일 수 없습니다.")
		}
	}

	// 추가 생성자는 이와 같이 다른 생성자를 불러서 만들 수 있다.
	constructor(name: String) : this(name, 30)
	constructor(age: Int) : this("username", age) {
		println("두번째 생성자") //Body를 가질 수도 있다.
	}
	constructor() : this("Dompoo") // 첫번째 부생성자를 호출하고, 이는 다시 주생성자를 호출한다.
}
// 참조하는 순서 : 세번째 부생성자 -> 첫번째 부생성자 -> 주생성자 -> init
// 불러지는 순서는 이와 반대다.

/**
 * 주의! 부생성자는 권장되지 않는다.
 * 생성자에 default parameter를 넣는 것이 권장된다.
 * 만약 정말 필요하다면, 정적 팩토리 메서드로 대체할 수 있다.
 */
class Person6(
	val name: String = "Dompoo",
	var age: Int = 30
) {
	// 생성자가 호출되는 시점에 사용된다.
	// 생성자 파라미터 검증등을 진행할 수 있다.
	init {
		if (age <= 0) {
			throw IllegalArgumentException("나이는 ${age}일 수 없습니다.")
		}
	}
}

/**
 * 멤버 함수
 * 멤버 함수를 사용하거나, 커스텀 getter를 사용한 프로퍼티를 만들 수도 있다.
 * 어떤 것을 사용하는지는 해당 기능이 함수의 기능인지, 프로퍼티인지를 판단해서 고른다.
 */
class Person7(
	val name: String = "Dompoo",
	var age: Int = 30
) {
	// 이렇게 똑같이 만들 수 있다.
	fun isAdult(): Boolean {
		return this.age >= 20
	}
	// 다만, 프로퍼티처럼 사용하도록 할 수 있다.
//	val isAdult:Boolean
//		get() { // 커스텀 getter
//			return this.age >= 20
//		}
//		get() = this.age >= 20
}

fun PersonUse() {
	val p1 = Person7()
	val adult1: Boolean = p1.isAdult()
//	val adult2: Boolean = p1.isAdult
}