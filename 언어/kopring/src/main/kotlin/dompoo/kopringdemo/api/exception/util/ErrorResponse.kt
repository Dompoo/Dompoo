package dompoo.kopringdemo.api.exception.util

data class ErrorResponse(
	val code: Int,
	val message: String,
) {
	companion object {
		fun from(exception: CustomException): ErrorResponse = ErrorResponse(
			code = exception.code,
			message = exception.message ?: ""
		)
	}
}