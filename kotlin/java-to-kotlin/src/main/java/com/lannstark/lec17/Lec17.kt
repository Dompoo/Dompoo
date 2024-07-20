package com.lannstark.lec17

val fruitList = listOf(
	Fruit("사과", 1000),
	Fruit("사과", 1200),
	Fruit("사과", 1200),
	Fruit("사과", 1500),
	Fruit("바나나", 3000),
	Fruit("바나나", 3200),
	Fruit("바나나", 2500),
	Fruit("수박", 10000)
)

/**
 * 자바는 함수를 전달하는 것처럼 보이지만, 사실은 함수형인터페이스를 구현한 익명객체를 넘기는 거였다.
 * 하지만 코틀린에서는 변수에 진짜로 함수를 넣을 수 있다.
 */
fun main1() {
	// 람다를 만드는 방법 1
	val isApple = fun(fruit: Fruit): Boolean {
		return fruit.name == "사과"
	}

	// 람다를 만드는 방법 2 -> 난 이거
	val isApple2 = {fruit:Fruit -> fruit.name == "사과"}

	isApple(Fruit("사과", 1000))

	// 람다도 타입이 있다.
	// 타입은 (Fruit) -> Boolean 등
	// "(파라미터타입) -> 반환타입"이다.
}

/**
 * 실제 사용
 */
fun filterFruit(
	fruits: List<Fruit>,
	filter: (Fruit) -> Boolean // 실제 함수 타입을 적어준다.
) : List<Fruit> {
	val result = mutableListOf<Fruit>()
	for (fruit in fruits) {
		if (filter(fruit)) {
			result.add(fruit)
		}
	}
	return result
}

fun main() {
	val isApple = fun(fruit: Fruit): Boolean {
		return fruit.name == "사과"
	}

	filterFruit(fruitList, isApple)
	filterFruit(fruitList, { fruit -> fruit.name == "사과" })
	filterFruit(fruitList) { fruit -> fruit.name == "사과" } // 람다가 마지막 파라미터일 경우 중괄호로 바깥으로 뺄 수도 있다.
//	filterFruit(fruitList) { it.name == "사과" } // 파라미터가 1개일 경우 it을 사용할 수 있다.
}