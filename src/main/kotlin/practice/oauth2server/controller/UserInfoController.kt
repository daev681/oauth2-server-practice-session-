package practice.oauth2server.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserInfoController {
    @GetMapping("/userinfo")
    fun userInfo(@AuthenticationPrincipal principal: OidcUser): Map<String, Any> {
        return mapOf(
                "sub" to principal.name,
                "email" to (principal.attributes["email"] ?: "unknown"),
                "name" to (principal.attributes["name"] ?: "anonymous")
        )
    }
}