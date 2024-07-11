package com.lannstark.lec06

fun main() {

}

/**
 * 코틀린에서의 foreach문은 : 대신 in을 사용한다.
 */
fun kotlinFor() {
	val numbers = listOf(1, 2, 3, 4, 5)
	for (number in numbers) { //number : numbers
		println(number)
	}
}

/**
 * 코틀린에서의 1..3 은 IntRange 객체를 만드는 방법이다.
 * IntRange는 IntProgression(등차수열) 중에서 step이 1인 것이다.
 * 1..3 = 시작 1 끝 3 공차 1
 * 1..10 step 2 = 시작 1 끝 10 공차 2
 * 3 downTo 1 = 시작 3 끝 1 공차 -1
 */
fun kotlinFor2() {
	for (i in 1..3) {
		println(i)
	}
}
fun kotlinFor3() {
	for (i in 3 downTo 1) { //i = 3, i >= 1
		println(i)
	}
}
fun kotlinFor4() {
	for (i in 1..10 step 2) { //i += 2
		println(i)
	}
}

/**
 * while은 정말 똑같다.
 */
fun kotlinWhile() {
	var i = 0;
	while (i <= 100) {
		println(i)
		i++
	}
}