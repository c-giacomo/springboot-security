package it.security.jwt.resources;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/private")
public class PrivateController {

   @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Value getMessage() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Value value = new Value();
    	value.setString("Hello " + authentication.getPrincipal() + " from private API controller");
        return value;
    }
}

@Getter
@Setter
class Value {
	private String string;
}
