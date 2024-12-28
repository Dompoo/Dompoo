package dompoo.kopringdemo.service

import dompoo.kopringdemo.api.MemberSaveRequest
import dompoo.kopringdemo.api.exception.MemberNotFoundException
import dompoo.kopringdemo.api.exception.UsernameDuplicationException
import dompoo.kopringdemo.domain.Member
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate
import java.time.LocalDate.of
import java.time.Period

class MemberServiceTest : BehaviorSpec({

	val memberRepository = mockk<MemberRepository>()
	val memberService = MemberService(memberRepository)

	Context("회원 저장") {
		beforeTest {
			every { memberRepository.save(any()) } returns Member(
				username = username,
				birth = birth
			)
			every { memberRepository.existsByUsername("중복된 사용자명") } returns true
			every { memberRepository.existsByUsername(not(eq("중복된 사용자명"))) } returns false
		}

		Given("정상적인 요청이 왔을 때") {
			val request = MemberSaveRequest(
				username = username,
				birth = birth
			)

			When("저장을 시도하면") {
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
				Then("UsernameDuplicationException이 터져야 한다.") {
					val exception = shouldThrow<UsernameDuplicationException> {
						memberService.saveMember(request)
					}
					exception.code shouldBe 400
					exception.message shouldBe "중복된 사용자명입니다."
				}
			}
		}
	}

	Context("Id 회원 조회") {
		beforeTest {
			every { memberRepository.findByIdOrNull(not(eq(-1))) } returns Member(
				username = username,
				birth = birth
			)
			every { memberRepository.findByIdOrNull(-1) } returns null
		}

		Given("존재하는 Id로 요청이 왔을 때") {
			val id = 1L

			When("조회를 시도하면") {
				val result = memberService.findMemberById(id)
				Then("정상적으로 조회되어야 한다.") {
					result.username shouldBe username
					result.birth shouldBe birth
					result.age shouldBe Period.between(birth, LocalDate.now()).years
				}
			}
		}
		Given("존재하지 않는 Id로 요청이 왔을 때") {
			val id = -1L

			When("조회를 시도하면") {
				Then("MemberNotFoundException이 터져야 한다.")
				val exception = shouldThrow<MemberNotFoundException> {
					memberService.findMemberById(id)
				}
				exception.code shouldBe 404
				exception.message shouldBe "존재하지 않는 사용자입니다."
			}
		}
	}
}) {
	companion object {
		private const val username = "dompoo"
		private val birth = of(2000, 5, 17)
	}
}