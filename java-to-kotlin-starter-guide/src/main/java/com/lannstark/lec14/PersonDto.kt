package com.lannstark.lec14

/**
 * data
 *
 * Java의 Recode와 아주 유사하다.
 * data 클래스는 기본적으로
 * hashCode, toString, equals 메서드를 구현해준다.
 * 거기에 기본으로 제공되는 getter, setter 덕분에 아주 쉽게 dto를 만들 수 있다.
 * 거기에 named argument를 사용하면 builder까지 비슷하게 구현된다.
 */
data class PersonDto(
	val name: String,
	val age: Int,
)

fun main1() {
	val dto = PersonDto("dompoo", 25)
	dto.hashCode()
}

/**
 * enum
 */
enum class Country(
	val code: String
) {
	KOREA("KOR"),
	JAPAN("JAP")
}

fun useCounry(country: Country) {
	when (country) {
		Country.KOREA -> println("꼬레아!")
		Country.JAPAN -> println("자뽕!")
	}
	// 컴파일 타임에 이미 enum 인스턴스가 무엇이 있는지 알고 있으므로,
	// else가 필요없다.
}