package com.lannstark.lec13

import com.lannstark.lec13.JavaHouse.LivingRoom

fun main() {

}

/**
 * 중첩 클래스
 *
 * Java에서는
 * 내부 클래스가 static일 경우 외부 클래스를 참조하지 못한다.
 * 내부 클래스가 static이 아닐 경우 외부 클래스를 참조할 수 있다.
 * 내부 클래스가 외부 클래스를 참조하여 발생하는 문제가 있다. (안티패턴이다.)
 * -> 웬만하면 내부클래스는 static이어야 한다.
 *
 * Kotlin에서는 이를 기본적으로 static하도록 만들어 유도한다.
 */
class House1(
	var adress: String,
	var livingRoom: LivingRoom1
) {
	class LivingRoom1(
		var area: Double
	) {
	}
}

/**
 * 굳이굳이 참조하겠다면 inner를 붙여주면 된다.
  */
class House2(
	var adress: String,
	var livingRoom: LivingRoom2
) {
	inner class LivingRoom2 (
		private var area: Double
	) {
		val adress: String
			get() = this@House2.adress //외부클래스 참조는 @으로 한다.
	}
}