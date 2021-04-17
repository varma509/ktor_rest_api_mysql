package io.realworld.app.routes

import io.ktor.auth.*
import io.ktor.routing.*
import io.realworld.app.web.controllers.ProfileController


fun Routing.profiles(profileController: ProfileController) {
    route("profiles/{username}") {
        authenticate(optional = true) {
            get { profileController.get(this.context) }
        }
        authenticate {
            route("follow") {
                post { profileController.follow(this.context) }
                delete { profileController.unfollow(this.context) }
            }
        }
    }
}

