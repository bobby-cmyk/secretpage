package vttp.batch5.ssf.groupb.secretpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import vttp.batch5.ssf.groupb.secretpage.models.LoginForm;
import static vttp.batch5.ssf.groupb.secretpage.Constants.*;

@Controller
@RequestMapping
public class AuthController {

    @PostMapping("/auth")
    public String authLogin(@Valid LoginForm loginForm, 
                                    BindingResult bindings, 
                                    HttpSession sess, 
                                    Model model) 
    {
        if (bindings.hasErrors()) {
            return "login";
        }

        String currentUsername = loginForm.getUsername();
        String currentPassword = loginForm.getPassword();

        // If the current username and password are correct
        if (currentUsername.equals(_USERNAME) && currentPassword.equals(_PASSWORD)) {

            // Set the login information into the sess
            sess.setAttribute("loginForm", loginForm);

            // Return the secret page
            return "secret";
        }

        model.addAttribute("loginForm", new LoginForm());

        return "login";
    }

    @PostMapping("/logout")
    public String authLogout(HttpSession sess, Model model) {

        // Destroy the session
        sess.invalidate();

        model.addAttribute("loginForm", new LoginForm());

        return "login";
    }
}
