package io.realworld.app.routes

import io.ktor.auth.*
import io.ktor.routing.*
import io.realworld.app.web.controllers.ArticleController
import io.realworld.app.web.controllers.CommentController

fun Routing.articles(articleController: ArticleController, commentController: CommentController) {
    route("articles") {
        authenticate {
            get("feed") { articleController.feed(this.context) }
            route("{slug}") {
                route("comments") {
                    post { commentController.add(this.context) }
                    authenticate(optional = true) {
                        get { commentController.findBySlug(this.context) }
                    }
                    delete("{id}") { commentController.delete(this.context) }
                }
                route("favorite") {
                    post { articleController.favorite(this.context) }
                    delete { articleController.unfavorite(this.context) }
                }
                get { articleController.get(this.context) }
                put { articleController.update(this.context) }
                delete { articleController.delete(this.context) }
            }
            authenticate(optional = true) {
                get { articleController.findBy(this.context) }
            }
            post { articleController.create(this.context) }
        }
    }
}