package dompoo.kopringdemo.service

import dompoo.kopringdemo.api.MemberSaveRequest
import dompoo.kopringdemo.api.exception.MemberNotFoundException
import dompoo.kopringdemo.api.exception.UsernameDuplicationException
import dompoo.kopringdemo.api.response.MemberFindResponse
import dompoo.kopringdemo.api.response.MemberSaveResponse
import dompoo.kopringdemo.domain.Member
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(
	private val memberRepository: MemberRepository
) {
	fun saveMember(
		request: MemberSaveRequest
	): MemberSaveResponse {

		if (memberRepository.existsByUsername(request.username)) {
			throw UsernameDuplicationException()
		}

		val member = Member(
			username = request.username,
			birth = request.birth
		)

		return MemberSaveResponse.from(memberRepository.save(member))
	}

	fun findMemberById(
		memberId: Long
	): MemberFindResponse {

		val findMember = memberRepository.findByIdOrNull(memberId) ?: throw MemberNotFoundException()

		return MemberFindResponse.from(findMember)
	}
}