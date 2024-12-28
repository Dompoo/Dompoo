package dompoo.kopringdemo.api

import java.time.LocalDate

data class MemberSaveRequest(
	val username: String,
	val birth: LocalDate,
)