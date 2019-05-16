package com.esgi.mgb.security.service

import com.esgi.mgb.repository.UserRepository
import com.esgi.mgb.security.CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
open class CustomUserDetailsService (private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return CustomUserDetails(userRepository.findOneByUserName(username)!!)
    }

}