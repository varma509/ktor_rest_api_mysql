package io.realworld.app.ext

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import kotlinx.css.CSSBuilder

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
