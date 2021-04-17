package io.realworld.app.routes

import io.ktor.auth.*
import io.ktor.routing.*
import io.realworld.app.web.controllers.TagController


fun Routing.tags(tagController: TagController) {
    route("tags") {
        authenticate(optional = true) {
            get { tagController.get(this.context) }
        }
    }
}