package kr.hhp227.Webapp.service

import kr.hhp227.Webapp.model.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

interface UserService : UserDetailsService {
    fun registerUser(user: User?)

    fun changePassword(username: String?, oldPassword: String?, newPassword: String?): Boolean

    fun removeUser(username: String?)

    fun getPasswordEncoder(): PasswordEncoder
}