package com.lannstark.lec07

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

fun main() {

}

/**
 * try-catch 문도 똑같이 생겼다.
 */
fun parseIntOrThrow(str: String): Int {
	try {
		return str.toInt()
	} catch (e: NumberFormatException) {
		throw IllegalArgumentException("주어진 ${str}은 숫자 형식이 아닙니다.")
	}
}
//try-catch도 하나의 expression으로 표현할 수 있다.
fun parseIntOrThrow2(str: String): Int? {
	return try {
		str.toInt()
	} catch (e: NumberFormatException) {
		null
	}
}

/**
 * 체크예외, 언체크예외
 */
fun readFile() {
	// 체크예외를 따로 처리할 필요 없다.
	// 코틀린에서는 모든 예외를 언체크예외로 간주한다. -> 코드의 편의성
	val currentFile = File(".")
	val file = File(currentFile.absolutePath + "/a.txt")
	val reader = BufferedReader(FileReader(file))
	println(reader.readLine())
	reader.close()
}

/**
 * try with resources
 * 코틀린에서는 이것이 사라지고, .use 인라인확장함수를 사용한다.
 * .use가 끝나면 해당 자원을 반환한다.
 */
fun readFile2(path: String) {
	val file = File(path)
	BufferedReader(FileReader(file)).use {
		reader -> println(reader.readLine())
	}
}