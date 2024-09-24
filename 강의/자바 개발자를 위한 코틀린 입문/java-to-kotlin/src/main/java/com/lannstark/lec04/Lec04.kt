package com.lannstark.lec04

fun main() {

}

/**
 * 객체간 비교
 */
fun compareObject() {
    val javaMoney1 = JavaMoney(10L)
    val javaMoney2 = JavaMoney(20L)

    // 객체 간 비교연산자를 통해 compareTo를 호출할 수 있다.
    println(javaMoney1 < javaMoney2)
}

/**
 * 동등성, 동일성
 */
fun compareObject2() {
    val javaMoney1 = JavaMoney(10L)
    val javaMoney2 = JavaMoney(10L)
    val javaMoney3 = javaMoney2

    println(javaMoney1 == javaMoney2) // 동등성 비교, true
    println(javaMoney1 === javaMoney2) // 동일성 비교, false
    println(javaMoney2 === javaMoney3) //true
}

/**
 * 연산자 오버로딩
 * + * / 등의 기본 연산자를 객체에서 오버로딩할 수 있다.
 * 함수가 아닌 객체 + 객체 연산을 수행할 수 있다!
 */
