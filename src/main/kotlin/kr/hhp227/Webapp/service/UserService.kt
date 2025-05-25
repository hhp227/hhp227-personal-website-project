package kr.hhp227.Webapp.service

import kr.hhp227.Webapp.model.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

interface UserService : UserDetailsService {
    fun registerUser(user: User?)

    fun removeUser(username: String?)

    fun getPasswordEncoder(): PasswordEncoder
}