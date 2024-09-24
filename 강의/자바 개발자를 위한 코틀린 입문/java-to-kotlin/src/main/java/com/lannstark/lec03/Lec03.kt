package com.lannstark.lec03

import java.lang.IllegalArgumentException

fun main() {
    var number1 = 1 //Int
    var number2 = 1L //Long
    var number3 = 3.0 //Double
    var number4 = 3.0f //Float

    /**
     * 기본타입 타입변환
     * 코틀린에서의 타입변환은 무조건 명시적으로 이루어진다.
     * Long 에 Int를 넣더라도(넣는 쪽이 크기가 더 작더라도) 무조건 변환해줘야 한다.
     * toLong() 등 수많은 타입변환 메서드를 사용하면 된다.
     */
    number2 = number1.toLong()

    /**
     * Nullable간 타입변환
     * Nullable 변수의 경우 따로 처리를 해주어야 변환이 된다.
     */
    var number5: Int? = 10
    var number6: Long = number5?.toLong() ?: 10L
}


/**
 * 객체 타입 캐스팅
 */
fun printAgeIfPerson1(obj: Any) {
    if (obj is Person) {
        val person = obj as Person // 타입 캐스팅
        println(person.age)
    }
}
fun printAgeIfPerson2(obj: Any) {
    if (obj is Person) {
        val person = obj // 미리 검사했다면, 빠질 수 있다.
        println(person.age)
    }
}
fun printAgeIfPerson3(obj: Any) {
    if (obj !is Person) {
        throw IllegalArgumentException()
        // !is로 인스턴스가 아닌 경우를 처리할 수 있다.
    }
}
fun printAgeIfPerson4(obj: Any?) {
    if (obj is Person) {
        val person = obj as? Person // Person? 타입으로 만들 수 있다.
        println(person?.age) //컴파일러가 safeCall을 강제해준다.
    }
}

/**
 * Any, Unit, Nothing
 * Any : Java의 Object 타입이다.
 * 코틀린에서는 원시타입이 없기 때문에 모-든 타입이 정말로 Any 타입이다.
 * Unit : Java의 Void 타입이다.
 * 타입추론이 가능하기 때문에 대부분 생략 가능하다.
 * void가 아니라 Void와 같다. 즉 타입 인자로 사용 가능하다.
 * Nothing : 함수가 정상적으로 작동하지 않는다느 것을 의미
 * 무한루프함수나 무조건 예외를 던지는 함수등에서 사용한다.
 * 많이 사용하지 않는다.
 */

/**
 * String interpolation, indexing
 */
fun stringInterpolation1() {
    val name = "dompoo"
    val age = 20
    val str = "제 이름은 ${name}이고, 나이는 ${age}입니다."
    println(str)
}
fun stringTrimIndent() {
    val trimIndent = """
        여기에는 여러 줄의 String을
        되게 편하게 작성할 수 있어요.
        앞의 들여쓰기가 사라지기 때문이죠!
        그저 엔터만 잘 눌러주세요.
    """.trimIndent()
    println(trimIndent)
}
fun stringIndexing() {
    val str = "나는 dompoo입니다. 이것은 문자열 입니다."
    val char = str[10]
    println(char)
}