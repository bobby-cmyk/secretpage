package vttp.batch5.ssf.groupb.secretpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import vttp.batch5.ssf.groupb.secretpage.models.LoginForm;
import static vttp.batch5.ssf.groupb.secretpage.Constants.*;

import java.util.Random;

@Controller
@RequestMapping
public class AuthController {

    @PostMapping("/auth")
    public String authLogin(@Valid LoginForm loginForm, 
                                    BindingResult bindings, 
                                    HttpSession sess, 
                                    Model model) 
    {   
        // Syntax validation to remind users to at least provide a non-empty username and password
        if (bindings.hasErrors()) {
                model.addAttribute("loginAttempts", sess.getAttribute("loginAttempts"));
                String captcha = generateCaptcha();
                model.addAttribute("captcha", captcha);
                sess.setAttribute("captcha", captcha);

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

        // Initialise the number of times
        int loginAttempts = 0;

        // If there are login attempts previously, retrieve the count from sess 
        if (sess.getAttribute("loginAttempts") != null) {
            loginAttempts = (int) sess.getAttribute("loginAttempts");
        }

        // if login is unsuccessful, add the number of count and set it in the sess
        loginAttempts++;

        sess.setAttribute("loginAttempts", loginAttempts);
        model.addAttribute("loginAttempts", loginAttempts);

        ObjectError err = new ObjectError("globalError", "Username or password is incorrect");
        bindings.addError(err);

        System.out.printf("\n Login attempts: %d\n", loginAttempts);
        
        // Check how many unsuccessful login attempts for this user (current session)
        if (loginAttempts >= 3) {
            return "account_locked";
        }

        if (loginAttempts >= 2) {

            String captcha = generateCaptcha();
            model.addAttribute("captcha", captcha);
            sess.setAttribute("captcha", captcha);

            return "login";
        }        

        return "login";
    }

    @PostMapping("/logout")
    public String authLogout(HttpSession sess, Model model) {

        // Destroy the session
        sess.invalidate();

        model.addAttribute("loginForm", new LoginForm());

        return "login";
    }

    private String generateCaptcha() {

        String captcha = "";

        Random rand = new Random();

        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            // from puntuations to lowercase z in the ASCII
            char c = (char) (rand.nextInt(91) + 33);
            captcha += c;
        }
        return captcha;
    }
}
