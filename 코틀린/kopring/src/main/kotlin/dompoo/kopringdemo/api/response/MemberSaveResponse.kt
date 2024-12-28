package dompoo.kopringdemo.api.response

import dompoo.kopringdemo.domain.Member

data class MemberSaveResponse(
	val memberId: Long,
	val username: String,
) {
	companion object {
		fun from(member: Member): MemberSaveResponse = MemberSaveResponse(
			memberId = member.id,
			username = member.username
		)
	}
}
