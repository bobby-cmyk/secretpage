package vttp.batch5.ssf.groupb.secretpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path={"/secret"})
public class SecretController {
    
    @GetMapping
    public String getSecretPage(HttpSession sess) {

        // Check if there's a user is authenticated and is not null
        if (sess.getAttribute("loginForm") != null) {
            return "secret";
        }

        // If user is not authenticated, go back to login
        return "login"; 
    }
}
