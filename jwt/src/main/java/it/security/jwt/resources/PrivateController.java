package it.security.jwt.resources;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class PrivateController {

    @GetMapping
    public String getMessage() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello " + authentication.getPrincipal() + " from private API controller";
    }
}
