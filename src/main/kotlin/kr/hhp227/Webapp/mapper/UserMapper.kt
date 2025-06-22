package kr.hhp227.Webapp.mapper

import kr.hhp227.Webapp.model.User
import org.apache.ibatis.annotations.Mapper
import org.springframework.security.core.GrantedAuthority

@Mapper
interface UserMapper {
    fun getUser(username: String?): User?

    fun getAuthority(username: String?): MutableList<GrantedAuthority>

    fun addUser(user: User?)

    fun setUser(user: User?)

    fun addAuthority(user: User?)

    fun removeUser(username: String?)

    fun removeAuthority(username: String?)
}