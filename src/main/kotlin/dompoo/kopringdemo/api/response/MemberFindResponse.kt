package dompoo.kopringdemo.api.response

import dompoo.kopringdemo.domain.Member
import java.time.LocalDate

data class MemberFindResponse(
	val memberId: Long,
	val username: String,
	val birth: LocalDate,
	val age: Int,
) {
	companion object {
		fun from(member: Member): MemberFindResponse = MemberFindResponse(
			memberId = member.id,
			username = member.username,
			birth = member.birth,
			age = member.age
		)
	}
}