package org.example

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

class FbLoginController {

    def springSecurityService

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def timeline() {
        def timeline = {
            def user = SecUser.get(springSecurityService.principal.id)

            def posts = []
            if (user.following) {
                posts = Post.withCriteria {
                    'in'("user", user.following)
                    order("createdOn", "desc")
                }
            }
            [posts: posts, postCount: posts.size()]
        }
        render timeline as JSON
    }
}
