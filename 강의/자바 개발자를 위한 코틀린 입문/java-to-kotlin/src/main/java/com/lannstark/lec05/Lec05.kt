package com.lannstark.lec05

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

fun main() {

}

/**
 * 기본적으로 자바와 동일하게 생겼다.
 */
fun valdateScoreInNotNegatvie(score: Int) {
	if (score < 0) {
		throw IllegalStateException("${score}는 0보다 작을 수 없습니다.")
	}
}

/**
 * if-else문도 똑같이 생겼다.
 * 하지만, 코틀린에서의 if-else는 statement가 아니라 expression이다.
 * statement : 문, 하나의 값으로 도출되지 않을 수도 있는 것
 * expression : 식, 하나의 값으로 도출되는 것
 * statement의 범위가 더 넓다.
 * 쉽게 얘기하면, 자바의 3항 연산자를 코틀린은 기본적으로 지원한다고 얘기할 수 있다.
 * if-elseif-else도 똑같다.
 */
fun passOrFail(score: Int): String {
	if (score >= 50) {
		return "Pass"
	} else {
		return "Fail"
	}
}
fun passOrFail2(score: Int): String {
	return if (score >= 50) {
		"Pass"
	} else {
		"Fail"
	}
}
fun passOrFail3(score: Int): String {
	return if (score >= 70) {
		"Pass"
	} else if (score >= 50) {
		"Not Bad"
	} else {
		"Fail"
	}
}

/**
 * 범위 연산자 in
 */
fun passOrFail4(score: Int): String {
	if (score !in 0..100) {
		throw IllegalArgumentException("score는 0부터 100사이에 있어야 한다.")
	}
	return if (score >= 50) {
		"Pass"
	} else {
		"Fail"
	}
}

/**
 * switch를 대신해서 나온 when
 */
fun getGradeSwitch(score: Int): String {
	return when (score) {
		in 78..100 -> "A"
		77 -> "Lucky Vicky!"
		in 50..76 -> "B"
		else -> "F"
	}
}
// 이런식의 활용도 가능하다.
fun startWithA(str: Any): Boolean {
	return when (str) {
		is String -> str.startsWith("A") //str in String
		else -> false
	}
}
fun judgeNumber(number: Int): Boolean {
	return when (number) {
		-1, 0, 1 -> true // number == -1 ...
		else -> false
	}
}
fun judgeNumber2(number: Int): String {
	return when {
		number == 0 -> "0입니다." // number == 0
		number % 2 == 0 -> "짝수입니다."
		else -> "홀수입니다."
	}
}