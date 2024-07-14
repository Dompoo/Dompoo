package dompoo.kopringdemo.api.response

import dompoo.kopringdemo.service.dto.MemberDto
import java.time.LocalDate

data class MemberFindResponse(
	val memberId: Long,
	val username: String,
	val birth: LocalDate,
	val age: Int,
) {
	companion object {
		fun from(dto: MemberDto): MemberFindResponse = MemberFindResponse(
			memberId = dto.memberId,
			username = dto.username,
			birth = dto.birth,
			age = dto.age
		)
	}
}