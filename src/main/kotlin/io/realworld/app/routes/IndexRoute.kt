package io.realworld.app.routes

import io.ktor.application.*
import io.ktor.html.*
import io.realworld.app.web.view.LayoutTemplate
import kotlinx.html.link

import io.ktor.auth.*
import io.ktor.routing.*
import io.realworld.app.web.controllers.ArticleController
import io.realworld.app.web.controllers.CommentController

fun Routing.index() {
    get("/") {
        call.respondHtmlTemplate(LayoutTemplate()) {
            header{
                link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                +"Ktor"
            }
            content {
                articleTitle {
                    +"Hello from Ktor!"
                }
                articleText {
                    +"Kotlin Framework for creating connected systems."
                }
            }
        }
    }
}