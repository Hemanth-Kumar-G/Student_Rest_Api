package com.hemanth.app.repository

import com.hemanth.app.data.Student
import com.hemanth.app.data.StudentTable
import com.hemanth.app.model.StudentDoa
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

class StudentRepository : StudentDoa {
    override suspend fun insert(name: String, age: Int): Student? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {

            statement = StudentTable.insert { students ->
                students[StudentTable.name] = name
                students[StudentTable.age] = age
            }
        }

        return rowToStudent(statement?.resultedValues?.get(0))
    }

    override suspend fun getAllStudent(): List<Student> {
        return DatabaseFactory.dbQuery {
            StudentTable.selectAll().mapNotNull {
                rowToStudent(it)
            }
        }
    }

    override suspend fun getStudentById(userId: Int): Student? {
        return DatabaseFactory.dbQuery {
            StudentTable.select {
                StudentTable.userId.eq(userId)
            }.map {
                rowToStudent(it)
            }.singleOrNull()
        }
    }

    override suspend fun deleteById(userId: Int): Int {
        return DatabaseFactory.dbQuery {
            StudentTable.deleteWhere {
                StudentTable.userId.eq(userId)
            }
        }
    }

    override suspend fun update(userId: Int, name: String, age: Int): Int {
        return DatabaseFactory.dbQuery {
            StudentTable.update({ StudentTable.userId.eq(userId) }) { student ->
                student[StudentTable.name] = name
                student[StudentTable.age] = age
            }
        }
    }

    private fun rowToStudent(row: ResultRow?): Student? {
        return row?.let {
            Student(
                userId = row[StudentTable.userId],
                name = row[StudentTable.name],
                age = row[StudentTable.age]
            )
        }
    }
}