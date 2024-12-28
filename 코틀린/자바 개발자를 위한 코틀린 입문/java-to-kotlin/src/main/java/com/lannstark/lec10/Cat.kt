package com.lannstark.lec10

class Cat(
	species: String
) : Animal(species, 4) { // 상속 받을 때는 한칸 띄고 : 을 붙인다.
	override fun move() { //override 키워드로 구현한다.
		println("고양이가 한강위를...")
	}
}