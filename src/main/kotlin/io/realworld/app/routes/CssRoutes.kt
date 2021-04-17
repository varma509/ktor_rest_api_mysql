package io.realworld.app.routes

import io.ktor.application.*
import io.ktor.routing.*
import io.realworld.app.ext.respondCss
import kotlinx.css.*

fun Routing.css() {
    get("/styles.css") {
        call.respondCss {
            body {
                backgroundColor = Color.whiteSmoke
                margin(0.px)
            }
            rule("h1") {
                color = Color.cornflowerBlue
            }
        }
    }
}