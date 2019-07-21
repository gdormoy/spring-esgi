package com.esgi.mgb.security

import com.esgi.mgb.model.Role
import com.esgi.mgb.model.User
import org.apache.catalina.users.AbstractUser
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

open class CustomUserDetails : User, UserDetails {

    private val log = LoggerFactory.getLogger(CustomUserDetails::class.java)

	constructor(user: User) : super(user.firstname,
			user.lastname,
			user.email,
			user.passWord,
			user.userName,
			user.birthDate)

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles
                .stream()
                .map { role: Role ->
                    log.debug("Granting Authority to user with role: " + role.toString())
                    SimpleGrantedAuthority(role.toString())
                }
                .collect(Collectors.toList())
    }

    override fun getUsername(): String {
        return super.userName
    }

    override fun getPassword(): String {
        return super.passWord
    }

    override fun isEnabled(): Boolean {
        return super.enabled
    }

    override fun isCredentialsNonExpired(): Boolean {
        return super.credentialsNonExpired
    }

    override fun isAccountNonExpired(): Boolean {
        return super.accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return super.accountNonLocked
    }
}