package com.lannstark.lec14.sealed

sealed class SealedClass(
	val name: String,
	val age: Int
) {

}

/**
 * sealed class
 *
 * 인스턴스를 미리 만들어 놓고 다른 패키지에서 추가될 수 없도록 한다.
 * 즉, 컴파일 타임에 하위 클래스의 타입을 모두 기억할 수 있다.
 * 같은 패키지 내에 하위 클래스를 모두 만들어야 한다.
 *
 * 어떻게 보면 enum과 abstract의 짬뽕이다.
 */