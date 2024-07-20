package com.lannstark.lec10

class PenguinWithInterface (
) : Swimable, Flyable {

	override fun act() {
		super<Swimable>.act() // 중복되는 인터페이스를 구현할 때는 <>를 사용한다.
		super<Flyable>.act()
	}

	override fun swim() {
		println("수영해야지!")
	}

	override fun fly() {
		println("날아야지!")
	}
}