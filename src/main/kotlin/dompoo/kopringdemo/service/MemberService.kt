package dompoo.kopringdemo.service

import dompoo.kopringdemo.api.MemberSaveRequest
import dompoo.kopringdemo.domain.Member
import dompoo.kopringdemo.service.dto.MemberSaveDto
import org.springframework.stereotype.Service

@Service
class MemberService(
	private val memberRepository: MemberRepository
) {
	fun saveMember(
		request: MemberSaveRequest
	): MemberSaveDto {

		val member = Member(
			username = request.username,
			birth = request.birth
		)

		return MemberSaveDto.from(memberRepository.save(member))
	}
}