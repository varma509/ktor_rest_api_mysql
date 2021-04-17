package io.realworld.app.routes

import io.ktor.auth.*
import io.ktor.routing.*
import io.realworld.app.web.controllers.UserController

fun Routing.users(userController: UserController) {
    route("users") {
        post { userController.register(this.context) }
        post("login") { userController.login(this.context) }
    }
    route("user") {
        authenticate {
            get { userController.getCurrent(this.context) }
            put { userController.update(this.context) }
        }
    }
}