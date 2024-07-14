package dompoo.kopringdemo.api.response

import dompoo.kopringdemo.service.dto.MemberSaveDto

data class MemberSaveResponse(
	val memberId: Long,
	val username: String,
) {
	companion object {
		fun from(dto: MemberSaveDto): MemberSaveResponse = MemberSaveResponse(
			memberId = dto.memberId,
			username = dto.username
		)
	}
}
