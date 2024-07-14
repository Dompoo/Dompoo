package dompoo.kopringdemo.service

import dompoo.kopringdemo.api.MemberSaveRequest
import dompoo.kopringdemo.domain.Member
import dompoo.kopringdemo.service.dto.MemberSaveDto
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class MemberService(
	private val memberRepository: MemberRepository
) {
	fun saveMember(
		request: MemberSaveRequest
	): MemberSaveDto {

		if (memberRepository.existsByUsername(request.username)) {
			throw IllegalArgumentException("중복된 사용자명입니다.")
		}

		val member = Member(
			username = request.username,
			birth = request.birth
		)

		return MemberSaveDto.from(memberRepository.save(member))
	}
}