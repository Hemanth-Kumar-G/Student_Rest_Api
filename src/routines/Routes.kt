package com.hemanth.app.routines

import com.hemanth.app.API_VERSION
import com.hemanth.app.repository.StudentRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val CREATE_STUDENT = "$API_VERSION/student"

@KtorExperimentalLocationsAPI
@Location(CREATE_STUDENT)
class CreateStudent

@KtorExperimentalLocationsAPI
fun Route.student(
    db: StudentRepository
) {

    post<CreateStudent> {
        val parameterName = call.receive<Parameters>()

        val name = parameterName["name"] ?: return@post call.respondText(
            "MISSING name",
            status = HttpStatusCode.Unauthorized
        )
        val age = parameterName["age"] ?: return@post call.respondText(
            "MISSING age",
            status = HttpStatusCode.Unauthorized
        )

        try {
            val student = db.insert(name, age.toInt())

            student?.userId?.let {
                call.respond(status = HttpStatusCode.OK, student)
            }
        } catch (e: Exception) {
            call.respondText(e.message ?: "Something went Wrong")
        }

    }

    get<CreateStudent> {
        try {
            val studentList = db.getAllStudent().sortedBy { it.userId }
            call.respond(status = HttpStatusCode.OK, studentList)
        } catch (e: Exception) {
            call.respondText(e.message ?: "Something went Wrong")
        }

    }

    delete("$API_VERSION/student/{id}") {
        val id = call.parameters["id"] ?: return@delete call.respondText(
            "No Id",
            status = HttpStatusCode.Unauthorized
        )
        val result = db.deleteById(id.toInt())
        try {
            if (result == 1)
                call.respondText("$id Deleted successfully")
            else
                call.respondText("$id NOT FOUND")

        } catch (e: Exception) {
            call.respondText(e.message ?: "Something went Wrong")
        }
    }

    get("$API_VERSION/student/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "NO Id ",
            status = HttpStatusCode.Unauthorized
        )
        val result = db.getStudentById(id.toInt())

        try {
            call.respond(status = HttpStatusCode.OK, result!!)
        } catch (e: Exception) {
            call.respondText(e.message ?: "Something went Wrong")
        }
    }

    put("$API_VERSION/student/{id}") {
        val id = call.parameters["id"] ?: return@put call.respondText(
            "id not found",
            status = HttpStatusCode.BadRequest
        )
        val updateParameter = call.receive<Parameters>()
        val name = updateParameter["name"] ?: return@put call.respondText(
            "name missing",
            status = HttpStatusCode.BadRequest
        )
        val age = updateParameter["age"] ?: return@put call.respondText(
            "age missing",
            status = HttpStatusCode.BadRequest
        )

        try {
            val result = id.toInt().let { it1 -> db.update(it1, name , age.toInt() ) }
            if(result == 1 ){
                call.respondText("updated successfully....")
            }else{
                call.respondText("something went wrong..")
            }
        }catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating User")
        }
    }

}