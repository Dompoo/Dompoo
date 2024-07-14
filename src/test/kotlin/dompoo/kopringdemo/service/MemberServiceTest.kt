package dompoo.kopringdemo.service

import dompoo.kopringdemo.api.MemberSaveRequest
import dompoo.kopringdemo.domain.Member
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate.of

class MemberServiceTest : BehaviorSpec({

	val memberRepository = mockk<MemberRepository>()
	val memberService = MemberService(memberRepository)

	Context("회원 저장") {
		every { memberRepository.save(any()) } returns Member(
			username = username,
			birth = birth
		)
		every { memberRepository.existsByUsername("중복된 사용자명") } returns true
		every { memberRepository.existsByUsername(not(eq("중복된 사용자명"))) } returns false

		Given("정상적인 요청이 왔을 때") {
			val request = MemberSaveRequest(
				username = username,
				birth = birth
			)

			When("저장하면") {
				val savedMember = memberService.saveMember(request)

				Then("정상적으로 저장되어야 한다.") {
					savedMember.username shouldBe username
				}
			}
		}

		Given("중복된 사용자명으로 요청이 왔을 때") {
			val request = MemberSaveRequest(
				username = "중복된 사용자명",
				birth = birth
			)

			When("저장을 시도하면") {
				Then("예외가 터져야 한다.") {
					val exception = shouldThrow<IllegalArgumentException> {
						memberService.saveMember(request)
					}
					exception.message shouldBe "중복된 사용자명입니다."
				}
			}
		}
	}
}) {
	companion object {
		private const val username = "dompoo"
		private val birth = of(2000, 5, 17)
	}
}