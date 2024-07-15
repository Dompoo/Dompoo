package dompoo.kopringdemo.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.ninjasquad.springmockk.MockkBean
import dompoo.kopringdemo.api.exception.MemberNotFoundException
import dompoo.kopringdemo.api.exception.UsernameDuplicationException
import dompoo.kopringdemo.service.MemberService
import dompoo.kopringdemo.service.dto.MemberDto
import dompoo.kopringdemo.service.dto.MemberSaveDto
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDate

@WebMvcTest(MemberController::class)
class MemberControllerTest : ExpectSpec() {

	override fun extensions() = listOf(SpringExtension)

	@Autowired
	lateinit var mockMvc: MockMvc

	@MockkBean
	lateinit var memberService: MemberService

	private val objectMapper = ObjectMapper().registerModule(JavaTimeModule())

	init {


		context("회원 저장") {
			beforeTest {
//			mockMvc = MockMvcBuilders
//				.standaloneSetup(MemberController(memberService))
//				.build()
				every { memberService.saveMember(match {it.username != duplicatedUsername}) } returns MemberSaveDto(
					memberId = memberId,
					username = username
				)
				every { memberService.saveMember(match {it.username == duplicatedUsername}) } throws UsernameDuplicationException()
			}

			expect("회원이 정상적으로 저장되어야 한다.") {
				val json = objectMapper.writeValueAsString(
					MemberSaveRequest(
						username = username,
						birth = birth
					))

				mockMvc.post("/member") {
					contentType = MediaType.APPLICATION_JSON
					content = json
				}.andExpectAll {
					status { isOk() }
					jsonPath("$.memberId").value(memberId)
					jsonPath("$.username").value(username)
				}.andDo { print() }
			}

			expect("유저명이 중복이라면 예외 응답이 와야 한다.") {
				val json = objectMapper.writeValueAsString(
					MemberSaveRequest(
						username = duplicatedUsername,
						birth = birth
					))

				mockMvc.post("/member") {
					contentType = MediaType.APPLICATION_JSON
					content = json
				}.andExpectAll {
					status { isBadRequest() }
					jsonPath("$.code").value(400)
					jsonPath("$.message").value("중복된 사용자명입니다.")
				}
			}

		}

		context("Id 회원 조회") {
			beforeTest {
				every { memberService.findMemberById(not(eq(notExistId))) } returns MemberDto(
					memberId = memberId,
					username = username,
					birth = birth,
					age = age
				)
				every { memberService.findMemberById(notExistId) } throws MemberNotFoundException()
			}

			expect("회원이 정상적으로 조회되어야 한다.") {
				mockMvc.get("/member/$memberId")
					.andExpectAll {
						status { isOk() }
						jsonPath("$.memberId").value(memberId)
						jsonPath("$.username").value(username)
						jsonPath("$.birth").value(birth)
						jsonPath("$.age").value(age)
					}.andDo { print() }
			}

			expect("Id가 없다면 예외 응답이 와야 한다.") {
				mockMvc.get("/member/$notExistId")
					.andExpectAll {
						status { isNotFound() }
						jsonPath("$.code").value(404)
						jsonPath("$.message").value("존재하지 않는 사용자입니다.")
					}.andDo { print() }
			}
		}
	}

	companion object {
		private const val memberId = 1L
		private const val notExistId = -1L
		private const val username = "dompoo"
		private const val duplicatedUsername = "duplicated"
		private val birth = LocalDate.of(2000, 5, 17)
		private val age = 25
	}
}