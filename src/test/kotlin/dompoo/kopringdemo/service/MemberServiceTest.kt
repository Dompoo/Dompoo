package dompoo.kopringdemo.service

import com.ninjasquad.springmockk.MockkBean
import dompoo.kopringdemo.domain.Member
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class MemberServiceTest : BehaviorSpec() {

	@MockkBean
	val memrepository = mockk<MemberRepository>()
	@MockkBean
	val memberService = mockk<MemberService>()

	init {
		Given("이런게 주어졌을 때,") {
			When("이렇게 하면,") {
				Then("이렇게 되어야 한다.") {
					1 + 2 shouldBe 3
				}
			}
		}
	}

	fun createMember(
		username: String = "dompoo",
		birth: LocalDate = LocalDate.of(2000, 5, 17)
	): Member =
		Member(
			username = username,
			birth = birth
		)
}