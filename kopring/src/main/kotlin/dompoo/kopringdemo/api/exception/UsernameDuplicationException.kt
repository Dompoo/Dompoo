package dompoo.kopringdemo.api.exception

import dompoo.kopringdemo.api.exception.util.CustomException

class UsernameDuplicationException(
	override val code: Int = CODE
) : CustomException(MESSAGE) {
	companion object {
		const val MESSAGE = "중복된 사용자명입니다."
		const val CODE = 400
	}
}