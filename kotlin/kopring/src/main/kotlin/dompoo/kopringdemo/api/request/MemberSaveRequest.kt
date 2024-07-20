package dompoo.kopringdemo.api.request

import java.time.LocalDate

data class MemberSaveRequest(
	val username: Long,
	val birth: LocalDate,
)