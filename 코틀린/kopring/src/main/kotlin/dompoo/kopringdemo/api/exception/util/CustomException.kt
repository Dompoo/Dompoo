package dompoo.kopringdemo.api.exception.util

abstract class CustomException(
	message: String,
	cause: Throwable? = null
) : RuntimeException(message, cause) {
	abstract val code: Int
}