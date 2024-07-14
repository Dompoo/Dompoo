package dompoo.kopringdemo.api

import dompoo.kopringdemo.api.response.MemberFindResponse
import dompoo.kopringdemo.api.response.MemberSaveResponse
import dompoo.kopringdemo.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MemberController(
	private val memberService: MemberService
) {
	@PostMapping("/member")
	fun saveMember(@RequestBody request: MemberSaveRequest): ResponseEntity<MemberSaveResponse> =
		ResponseEntity.ok().body(MemberSaveResponse.from(memberService.saveMember(request)))

	@GetMapping("/member/{id}")
	fun findMember(@PathVariable id: Long): ResponseEntity<MemberFindResponse> =
		ResponseEntity.ok().body(MemberFindResponse.from(memberService.findMemberById(id)))

}