package com.lannstark.lec01

fun main() {
    /**
     * Var과 Val
     */
    var number = 10 // 발, Variable, 바뀔 수 있는 변수
    val number2 = 10 // 밸, Value, 바뀔 수 없는 상수
    number = 1
    // number2 = 20 에러, 바뀔 수 없다.

    /**
     * 타입 추론
     */
    // 코틀린은 정적 타입 언어지만, 타입을 적어주지 않아도 된다.
    // 컴파일러가 타입을 추론해주기 때문이다.
    // 원한다면 타입을 지정해줄 수 있다.
    var str:String = "HelloWorld"
    println(str)

    /**
     * 실제 var val 사용
     */
    // 그렇다면 val 과 var을 어떻게 구분해서 사용해야 하는가?
    // 일단 무조건 val로 작성하고, 변할 일이 있다면 그때 var로 수정하자!
    // 코틀린에서는 원시타입과 참조타입을 구분하지 않는다.
    // 컴파일러가 알아서 다 해준다.

    /**
     * Nullable
     */
    // 코틀린에서의 nullable은 아예 다르게 표현한다.
    // null이 들어갈 수 있다면 타입에 ?를 붙인다.
    var number3:Long? = 10
    number3 = null

    /**
     * 객체 인스턴스 생성
     */
    // 코틀린에서는 객체 인스턴스를 생성할 때 new를 붙이지 않는다.
    var persen = Person("dompoo")
}