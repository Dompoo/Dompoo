package dompoo.kopringdemo.service.dto

import dompoo.kopringdemo.domain.Member

data class MemberSaveDto(
	val memberId: Long,
	val username: String,
) {
	companion object {
		fun from(member: Member): MemberSaveDto = MemberSaveDto(
			memberId = member.id,
			username = member.username,
		)
	}
}