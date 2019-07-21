package com.esgi.mgb.security.service

import com.esgi.mgb.dao.UserDAO
import com.esgi.mgb.security.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (private val userRepository: UserDAO) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return CustomUserDetails(userRepository.findOneByUserName(username))
    }

}