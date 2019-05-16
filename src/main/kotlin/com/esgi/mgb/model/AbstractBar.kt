package com.esgi.mgb.model

abstract class AbstractBar {
    abstract val id: String?
    abstract var name: String
    abstract var address: String
    abstract var owner: User
}