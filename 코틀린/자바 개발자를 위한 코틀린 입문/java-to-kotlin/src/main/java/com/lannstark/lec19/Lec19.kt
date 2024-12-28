package com.lannstark.lec19
import com.lannstark.lec18.Fruit as Fruit18
import com.lannstark.lec17.Fruit as Fruit17

data class Person (
	val name: String,
	val age: Int,
)

/**
 * TypeAlias
 *
 * 너무 긴 타입이나 중복되는 타입을 간단하게 사용할 수 있게 해준다.
 */
typealias MapPersonByName = Map<String, Person>

/**
 * as import
 *
 * 중복되는 것을 구분하여 import하기 위해 사용한다.
 * import시 원하는 이름을 지정하여 가져온다.
 */

/**
 * 구조분해
 *
 * 객체의 멤버 변수를 한번에 여러 변수에 할당할 수 있다.
 * data class에서 주로 사용된다.
 * val (name, age) = dto
 *
 * 원리 : data class는 componentN 함수를 자동으로 만들어주는데, 이를 축약해서 적었을 뿐이다.
 * val name = dto.component1()
 * val age = dto.component2()
 *
 * dataClass가 아닐때 구조분해를 사용하고 싶다면 직접 구현하면 된다.
 */
fun main1() {
	val (name, age) = Person("dompoo", 25)
	println("${name} : ${age}")
}

/**
 * return break continue 는 Java와 동일하게 사용된다.
 *
 * 단, 컬렉션의 forEach() 에서 break continue 가 기본적으로 사용 불가능하나,
 * run 블록으로 감싸서 할 수 있다.
 * -> 굳이? 그냥 for문을 사용하자.
 */

/**
 * label
 *
 * label을 통해 원하는 반복문에 영향을 줄 수 있다.
 * 단, 복잡도가 증가하므로 안티패턴이다.
 */
fun main2() {
	loop@ for (i in 1..100) {
		for (j in 1..100) {
			if (i == 1 && j == 2) {
				break@loop
			}
		}
	}
}