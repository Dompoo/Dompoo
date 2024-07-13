package dompoo.kopringdemo.api

import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*

class MemberControllerTest : ExpectSpec({
	context("이런 컨텍스트에서는") {
		expect("이렇게 되어야 한다.") {
			1 + 2 shouldBe 3
		}
	}
})