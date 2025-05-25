package kr.hhp227.Webapp.service

import kr.hhp227.Webapp.mapper.UserMapper
import kr.hhp227.Webapp.model.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper
) : UserService {
    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails? {
        return userMapper.getUser(username)?.apply {
            setAuthorities(userMapper.getAuthority(username))
        }
    }

    @Transactional
    override fun registerUser(user: User?) {
        val username = user?.username ?: ""
        val password = user?.password ?: ""
        val encodedPassword = passwordEncoder.encode(password)

        if (!username.isEmpty() && !password.isEmpty()) {
            if (userMapper.getUser(username) != null) return
            user?.setPassword(encodedPassword)
            userMapper.addUser(user)
            userMapper.addAuthority(user)
        }
    }

    override fun removeUser(username: String?) {
        userMapper.removeUser(username)
        userMapper.removeAuthority(username)
    }

    override fun getPasswordEncoder() = passwordEncoder
}
