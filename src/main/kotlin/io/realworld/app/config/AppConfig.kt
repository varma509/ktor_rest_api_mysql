package io.realworld.app.config

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.jackson.jackson
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.CIO
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineFactory
import io.ktor.server.engine.BaseApplicationEngine
import io.ktor.server.engine.EngineAPI
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI
import io.realworld.app.routes.*
import io.realworld.app.utils.JwtProvider
import io.realworld.app.web.ErrorResponse
import io.realworld.app.web.controllers.ArticleController
import io.realworld.app.web.controllers.CommentController
import io.realworld.app.web.controllers.ProfileController
import io.realworld.app.web.controllers.TagController
import io.realworld.app.web.controllers.UserController
import io.realworld.app.web.view.LayoutTemplate
import kotlinx.css.*
import kotlinx.html.link
import org.kodein.di.generic.instance

const val SERVER_PORT = 3000

@KtorExperimentalAPI
@EngineAPI
fun setup(isCio: Boolean = false): BaseApplicationEngine {
    DbConfig.setup("jdbc:mysql://root: @localhost:3306/test?useUnicode=true&serverTimezone=UTC", "root", "")
    return server(if (isCio) CIO else Netty)
}

@KtorExperimentalAPI
@EngineAPI
fun server(
    engine: ApplicationEngineFactory<BaseApplicationEngine,
        out ApplicationEngine.Configuration>
): BaseApplicationEngine {
    return embeddedServer(
        engine,
        port = SERVER_PORT,
        watchPaths = listOf("mainModule"),
        module = Application::mainModule
    )
}

@KtorExperimentalAPI
fun Application.mainModule() {
    val userController by ModulesConfig.kodein.instance<UserController>()
    val profileController by ModulesConfig.kodein.instance<ProfileController>()
    val articleController by ModulesConfig.kodein.instance<ArticleController>()
    val commentController by ModulesConfig.kodein.instance<CommentController>()
    val tagController by ModulesConfig.kodein.instance<TagController>()

    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }

    }
    install(Authentication) {
        jwt {
            verifier(JwtProvider.verifier)
            authSchemes("Token")
            validate { credential ->
                if (credential.payload.audience.contains(JwtProvider.audience)) {
                    userController.getUserByEmail(credential.payload.claims["email"]?.asString())
                } else null
            }
        }
    }
    install(StatusPages) {
        exception(Exception::class.java) {
            val errorResponse = ErrorResponse(mapOf("error" to listOf("detail", this.toString())))
            context.respond(
                HttpStatusCode.InternalServerError, errorResponse
            )
        }
    }

    install(Routing) {
        index()
        css()
        users(userController)
        profiles(profileController)
        articles(articleController, commentController)
        tags(tagController)
    }
}

