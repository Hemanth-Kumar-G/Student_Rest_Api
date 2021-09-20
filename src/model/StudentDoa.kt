package com.hemanth.app.model

import com.hemanth.app.data.Student

interface StudentDoa {

    suspend fun insert(
        name: String,
        age: Int
    ): Student?

    suspend fun getAllStudent(): List<Student>?

    suspend fun getStudentById(userId: Int): Student?

    suspend fun deleteById(userId: Int): Int

    suspend fun update(userId: Int, name: String, age: Int): Int
}