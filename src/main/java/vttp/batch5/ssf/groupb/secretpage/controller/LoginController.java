package vttp.batch5.ssf.groupb.secretpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.batch5.ssf.groupb.secretpage.models.LoginForm;

import static vttp.batch5.ssf.groupb.secretpage.Constants.*;

@Controller
@RequestMapping(path={"/", "/index", "/login"})
public class LoginController {

    @GetMapping()
    public String getLoginPage(Model model) {
        
        model.addAttribute("loginForm", new LoginForm());
        
        return "login";
    }

    @PostMapping("/auth")
    public String authLogin(LoginForm loginForm, HttpSession sess) {

        String currentUsername = loginForm.getUsername();
        String currentPassword = loginForm.getPassword();

        // If the current username and password are correct
        if (currentUsername.equals(_USERNAME) && currentPassword.equals(_PASSWORD)) {

            // Set the login information into the sess
            sess.setAttribute("loginForm", loginForm);

            // Return the secret page
            return "secret";
        }

        return "login";
    }
}
