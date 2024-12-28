package com.lannstark.lec11

/**
 * java의 접근 제어자
 * public : 모든 곳
 * protected : 같은 패키지 + 하위 클래스
 * default : 같은 패키지 (기본)
 * private : 자신 클래스
 *
 * 코틀린에서는 패키지를 '접근 제어'의 단위로 사용하지 않고, 무조건 namespace분리로만 사용한다.
 * kotilin의 접근 제어자
 * public : 모든 곳 (기본)
 * protected : 자신 클래스 + 하위 클래스
 * internal : 같은 모듈 -> 한 gradle 프로젝트 등
 * private : 자신 클래스
 *
 * 자바와 코틀린 같이 사용중 주의점
 * 자바 입장에서는 코틀린의 internal을 같은 패키지에서 호출할 수 있다.
 * 자바 입장에서는 코틀린의 protected를 같은 패키지에서 호출할 수 있다.
 */