package com.lannstark.lec10

abstract class Animal(
	protected val species: String,
	protected open val legCount: Int
	// override하기 위해서는 open을 붙여야 한다.
	// 기본적으로 final, 즉 override를 막고 있기 때문이다.

) {
	abstract fun move()
}