package dompoo.kopringdemo.api

import dompoo.kopringdemo.api.request.MemberSaveResponse
import dompoo.kopringdemo.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
	private val memberService: MemberService
) {
	@PostMapping("/member")
	fun saveMember(@RequestBody request: MemberSaveRequest): ResponseEntity<MemberSaveResponse> =
		ResponseEntity.ok().body(MemberSaveResponse.from(memberService.saveMember(request)))
}