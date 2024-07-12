package com.lannstark.lec15

/**
 * 코틀린의 컬렉션은
 * List Set Map 뿐만 아니라,
 * Mutable이 붙은 List Set Map이 존재한다.
 *
 * Mutable이 붙으면 컬렉션에 element를 추가/삭제할 수 있다. (가변)
 * 안붙으면 추가/삭제할 수 없다. (단, element를 수정하는 것은 가능(참조니까))
 *
 * 코틀린은 쉽게 제공하는걸 먼저 사용하고, 어렵게 제공하는걸 나중에 사용하도록 유도한다.
 * 그러니, 기본적으로 불변컬렉션을 사용하고, 필요한 경우 가변으로 교체하는 것을 추천한다.
 */

fun list() {
	var list = listOf(10, 20, 30) // 불변
	val emptyList = emptyList<Int>() // 불변, 자료형을 붙여줘야한다. (아무래도)

	val muList = mutableListOf(10, 20, 30)
	val emptyMuList = mutableListOf<Int>()

	// for-each
	for (element in muList) {
		println(element)
	}
	// for
	for ((index, element) in muList.withIndex()) {
		println("${index} : ${element}")
	}
}

/**
 * Set은 list와 사용법이 동일
 */

fun main() {
	val muMap = mutableMapOf<Long, String>()
	muMap[0] = "hello"
	muMap[1] = "world"
	muMap.put(2, "Or, Kotlin!!")

	println(muMap)

	//나라면 이걸 쓸듯, 다른 사람은 어떠려나?
	for (element in muMap) {
		println("${element.key} : ${element.value}")
	}

	for (key in muMap.keys) {
		println("${key} : ${muMap[key]}")
	}

	for ((key, value) in muMap.entries) {
		println("${key} : ${value}")
	}
}

/**
 * 컬렉션의 null 가능성
 *
 * List<Int>? : element는 null 넣기 불가능, list 자체는 null 가능
 * List<Int?> : element는 null 넣기 가능, list 자체는 null 불가능
 * List<Int?>? : element는 null 넣기 가능, list 자체도 null 가능
 */


/**
 * 자바는 코틀린과 다르게
 * 1. 불변/가변 컬렉션 개념 x
 * 2. nullable 개념 x
 * -> 자바와 코드를 주고 받을 때,
 * 1. 컬렉션의 내용이 자바에 의해 변경될 수 있음을 감안해야 한다.
 * 2. 맥락에 따라 잘 랩핑해줘야 한다.
 */
