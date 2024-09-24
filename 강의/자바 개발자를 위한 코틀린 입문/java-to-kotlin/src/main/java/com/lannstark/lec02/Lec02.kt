package com.lannstark.lec02

import java.lang.IllegalArgumentException

fun main() {

}

// safeCall : null이라면 실행하지 않는다.
// elvis연산자 : null이라면 실행한다.
fun startWithA1(str: String?): Boolean {
//    if (str == null) {
//        throw IllegalArgumentException("null이 들어왔습니다")
//    }
//    return str.startsWith("A")

    return str?.startsWith("A")
        ?: throw IllegalArgumentException("null이 들어왔습니다")
}

fun startWithA2(str: String?): Boolean? {
//    if (str == null) {
//        return null
//    }
//    return str.startsWith("A")
    return str?.startsWith("A")
}

fun startWithA3(str: String?): Boolean {
//    if (str == null) {
//        return false
//    }
//    return str.startsWith("A")
    return str?.startsWith("A")
        ?: false
}

// null 단언
fun startWithANotNull(str: String?): Boolean {
    // 이 변수는 null이 아니라고 단언! -> 컴파일러에게 예외로 해주세요!
    // 만약 null이 들어온다면 NPE 발생
    return str!!.startsWith("A")
}

/**
 * 플랫폼 타입
 * 자바 코드등을 가져와서 활용할 때,
 * 따로 @Nullable @NotNull 등 null 설정을 쓰지 않는다면,
 * 컴파일러가 오류를 출력하지 않으므로 런타임예외가 터질 수 있다.
 * -> 내 코드라면 null 설정을 적어주고,
 * 라이브러리등 내 코드가 아니라면 코드를 읽으면서 신중을 가해야 한다.
 */
