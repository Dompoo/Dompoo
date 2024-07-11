package com.lannstark.lec10

class Penguin (
	species: String
) : Animal(species, 2) {

	private val wingCount: Int = 2

	override fun move() {
		println("펭귄펭귄")
	}

	override val legCount: Int
		get() = this.wingCount + super.legCount
}