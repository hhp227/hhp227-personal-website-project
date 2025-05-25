package kr.hhp227.Webapp.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(
    private var authorities: MutableCollection<out GrantedAuthority?>? = null,
    private var username: String? = null,
    private var password: String? = null,
    private var isAccountNonExpired: Boolean = false,
    private var isAccountNonLocked: Boolean = false,
    private var isCredentialsNonExpired: Boolean = false,
    private var isEnabled: Boolean = false
) : UserDetails {
    override fun isAccountNonExpired(): Boolean {
        return isAccountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return isAccountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isCredentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return authorities
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }

    fun setAuthorities(authorities: MutableList<GrantedAuthority>?) {
        this.authorities = authorities
    }

    fun setAccountNonExpired(isAccountNonExpired: Boolean) {
        this.isAccountNonExpired = isAccountNonExpired
    }

    fun setAccountNonLocked(isAccountNonExpired: Boolean) {
        this.isAccountNonExpired = isAccountNonExpired
    }

    fun setCredentialsNonExpired(isCredentialsNonExpired: Boolean) {
        this.isCredentialsNonExpired = isCredentialsNonExpired
    }

    fun setEnabled(isEnabled: Boolean) {
        this.isEnabled = isEnabled
    }

    fun setPassword(password: String?) {
        this.password = password
    }
}
