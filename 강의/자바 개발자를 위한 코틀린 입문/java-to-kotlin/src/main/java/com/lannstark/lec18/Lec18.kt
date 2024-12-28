package com.lannstark.lec18

import com.lannstark.lec17.Fruit

fun main() {
	fruitList.stream()
		.filter { fruit -> fruit.name == "사과" }

	// 굳이 stream이 아니어도 코틀린에서 컬렉션을 다루는 방법은 많다.

	// filter filterIndexed map mapIndexed mapNotNull
	// all none any
	// count sortedBy sortedByDecending distinct
	// first firstOrNull last lastOrNull
	// groupBy associateBy associateWith ...

}

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