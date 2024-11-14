package vttp.batch5.ssf.groupb.secretpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.batch5.ssf.groupb.secretpage.models.LoginForm;

@Controller
@RequestMapping(path={"/secret"})
public class SecretController {
    
    @GetMapping
    public String getSecretPage(Model model, HttpSession sess) {

        // Check if there's a user is authenticated and is not null
        if (sess.getAttribute("loginForm") != null) {
            return "secret";
        }

        model.addAttribute("loginForm", new LoginForm());

        // If user is not authenticated, go back to login
        return "login"; 
    }
}
