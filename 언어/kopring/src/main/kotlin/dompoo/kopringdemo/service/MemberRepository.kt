package dompoo.kopringdemo.service

import dompoo.kopringdemo.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

	fun existsByUsername(username: String): Boolean

	fun findByUsername(username: String): Member?
}