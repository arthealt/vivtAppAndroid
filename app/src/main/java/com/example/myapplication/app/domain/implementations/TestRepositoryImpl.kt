package com.example.myapplication.app.domain.implementations

import com.example.myapplication.app.data.remote.AuthService
import com.example.myapplication.app.data.remote.models.Auth
import com.example.myapplication.app.data.storage.RoomAppDatabase
import com.example.myapplication.app.data.storage.entity.People
import com.example.myapplication.app.domain.TestRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(private val roomAppDatabase: RoomAppDatabase, private val authService: AuthService): TestRepository {

    override suspend fun auth(email: String, password: String): Deferred<Auth> {
        return GlobalScope.async {
            authService.authAsync(email, password)
        }
    }

    override suspend fun uploadLocal(people: People) {
        GlobalScope.async {
            roomAppDatabase.peopleDao().insert(people)
        }
    }

    override suspend fun fetchLocal(): Deferred<List<People>> {
        return GlobalScope.async {
            roomAppDatabase.peopleDao().getAll()
        }
    }
}