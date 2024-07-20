package com.lannstark.lec10

interface Flyable {

	fun act() {
		println("파닥파닥") // default 함수를 그냥 만들 수 있다.
	}

	fun fly()

}