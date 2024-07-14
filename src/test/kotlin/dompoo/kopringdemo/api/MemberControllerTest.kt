package dompoo.kopringdemo.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dompoo.kopringdemo.service.MemberService
import dompoo.kopringdemo.service.dto.MemberSaveDto
import io.kotest.core.spec.style.ExpectSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

class MemberControllerTest : ExpectSpec({

	val memberService = mockk<MemberService>()
	lateinit var mockMvc: MockMvc
	lateinit var objectMapper: ObjectMapper

	beforeTest {
		mockMvc = MockMvcBuilders
			.standaloneSetup(MemberController(memberService))
			.build()
		objectMapper = ObjectMapper()
			.registerModule(JavaTimeModule())
	}

	context("회원 저장") {
		every { memberService.saveMember(any()) } returns MemberSaveDto(
			memberId = memberId,
			username = username
		)
		val json = objectMapper.writeValueAsString(
			MemberSaveRequest(
				username = username,
				birth = birth
			))
		expect("이렇게 되어야 한다.") {
			mockMvc.post("/member") {
				contentType = MediaType.APPLICATION_JSON
				content = json
			}.andExpectAll {
				status { isOk() }
				jsonPath("$.memberId").value(memberId)
				jsonPath("$.username").value(username)
			}.andDo { print() }
		}
	}

}) {
	companion object {
		private const val memberId = 1L
		private const val username = "dompoo"
		private val birth = LocalDate.of(2000, 5, 17)
	}
}