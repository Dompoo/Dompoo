package dompoo.kopringdemo.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class MemberServiceTest : BehaviorSpec({
	Context("이런 컨텍스트에서") {
		Given("이런게 주어졌을 때,") {
			When("이렇게 하면,") {
				Then("이렇게 되어야 한다.") {
					1 + 2 shouldBe 3
				}
			}
		}
	}
})
