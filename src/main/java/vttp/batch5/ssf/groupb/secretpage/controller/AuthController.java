package vttp.batch5.ssf.groupb.secretpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.node.NumericNode;

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

        // Retrieve the user input username and password
        String currentUsername = loginForm.getUsername();
        String currentPassword = loginForm.getPassword();
        
        // If the current username and password are incorrect
        if (!currentUsername.equals(_USERNAME) || !currentPassword.equals(_PASSWORD)) {

            // Add a global error that authentication failed.
            ObjectError err = new ObjectError("globalError", "Login failed. Incorrect username/password.");
            bindings.addError(err);

            // Initialise the number of times
            int loginAttempts = 0;

            // If there are login attempts previously, retrieve the count from sess 
            if (sess.getAttribute("loginAttempts") != null) {
                loginAttempts = (int) sess.getAttribute("loginAttempts");
            }

            // Add to count, set to sess, and add to model
            loginAttempts++;
            sess.setAttribute("loginAttempts", loginAttempts);
            model.addAttribute("loginAttempts", loginAttempts);

            System.out.printf("\n Login attempts: %d\n", loginAttempts);
            
            // Check how many unsuccessful login attempts for this user (current session)
            if (loginAttempts >= 3) {
                return "account_locked";
            }

            else if (loginAttempts >= 2) {

                String captcha = generateCaptcha();
                model.addAttribute("captcha", captcha);
                sess.setAttribute("captcha", captcha);

                return "login";
            }

            return "login";
        }

        // If authentication is successful...

        // Set the login information into the sess to keep user logged in 
        sess.setAttribute("loginForm", loginForm);

        // Return the secret page
        return "secret";   
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

        for (int i = 0; i < CAPTCHA_LENGTH/3; i++) {

            char upperC = (char) (rand.nextInt(ALPHABET_COUNT) + START_INDEX_UPPER);
            char lowerC = (char) (rand.nextInt(ALPHABET_COUNT) + START_INDEX_LOWER);
            char numberC = (char) (rand.nextInt(NUMBER_COUNT) + START_INDEX_NUMBER);

            captcha += upperC;
            captcha += lowerC;
            captcha += numberC;
        }
        return captcha;
    }
}
