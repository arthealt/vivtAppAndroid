package com.example.myapplication.app.domain

import com.example.myapplication.app.data.remote.models.Auth
import com.example.myapplication.app.data.storage.entity.People
import kotlinx.coroutines.Deferred

interface TestRepository {

    suspend fun auth(email: String, password: String): Deferred<Auth>

    suspend fun uploadLocal(people: People)

    suspend fun fetchLocal(): Deferred<List<People>>

}