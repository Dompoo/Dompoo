package dompoo.kopringdemo.api.exception

import dompoo.kopringdemo.api.exception.util.CustomException

class MemberNotFoundException(
	override val code: Int = CODE
) : CustomException(MESSAGE) {
	companion object {
		const val MESSAGE = "존재하지 않는 사용자입니다."
		const val CODE = 404
	}
}