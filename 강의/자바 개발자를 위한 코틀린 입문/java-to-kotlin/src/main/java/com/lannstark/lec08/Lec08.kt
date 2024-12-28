package com.lannstark.lec08

/**
 * 여러번 축약형 모습
 */
fun max(a: Int, b: Int): Int {
	if (a > b) return a
	else return b
}
// return을 모을 수 있다.
fun max1(a: Int, b: Int): Int {
	return if (a > b) a
	else b
}
// body가 하나의 값으로 간주될 경우 괄호를 지우고 =을 쓸 수 있다.
fun max2(a: Int, b: Int): Int =
	if (a > b) a
	else b
// 반환타입추론을 할 수 있다.
fun max3(a: Int, b: Int) = if (a > b) a else b

/**
 * default parameter, named argument
 */
fun repeat(str: String, num: Int = 3, newLine: Boolean = true) {
	for (i in 1..num) {
		if (newLine) {
			println(str)
		} else {
			print(str)
		}
	}
}
fun useRepeat() {
	repeat("hello", 3, true)
	repeat("hello", 3)
	repeat("hello")

	repeat("hello", newLine = false)
	// 직접 지정하여 값을 넣을 수 있다.
	// 이는 빌더 패턴과 같은 효과를 갖는다.
	repeat(str = "hello", num = 3, newLine = false)
}

/**
 * 가변인자
 * ...대신에 vararg를 써야한다.
 * vararg에 배열을 바로 넣는 대신에 spread연산자 *을 붙여주어야 한다.
 */
fun printAll(vararg strs: String) {
	for (str in strs) {
		println(str)
	}
}
fun usePrintAll() {
	val args = arrayOf("Hello", "World")
	printAll(*args)
}