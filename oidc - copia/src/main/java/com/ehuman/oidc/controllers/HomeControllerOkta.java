package com.ehuman.oidc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "*")
@RestController
public class HomeControllerOkta {
	
	private static final Logger LOG = (Logger) LoggerFactory.getLogger(HomeControllerOkta.class);
	
	
    @GetMapping("/humanSSO")
    public ModelAndView home(@AuthenticationPrincipal OidcUser user, HttpServletRequest request) throws ResponseStatusException {
    	LOG.info("user: "+ user.getFullName());
    	
    	if(user.getEmailVerified()){//trae boleano
    	
    		return new ModelAndView("forward:/humanSSO/valida?email="+user.getEmail());
    	}else {
    		LOG.info("Ocurrio error");
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found");
        		//"Welcome, "+ user.getFullName() + user.getEmail()+ user.getAuthorizationCodeHash();
    }
    }

}
