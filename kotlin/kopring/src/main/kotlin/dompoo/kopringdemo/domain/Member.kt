package dompoo.kopringdemo.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate
import java.time.Period

@Entity
class Member(
	@Column
	var username: String,
	@Column
	var birth: LocalDate
) {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long = 0

	var age:Int = Period.between(birth, LocalDate.now()).years
}