package dompoo.kopringdemo.service.dto

import dompoo.kopringdemo.domain.Member
import java.time.LocalDate

data class MemberDto(
	val memberId: Long,
	val username: String,
	val birth: LocalDate,
	val age: Int,
) {
	companion object {
		fun from(member: Member): MemberDto = MemberDto(
			memberId = member.id,
			username = member.username,
			birth = member.birth,
			age = member.age
		)
	}
}

