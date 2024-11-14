package vttp.batch5.ssf.groupb.secretpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.batch5.ssf.groupb.secretpage.models.LoginForm;

@Controller
@RequestMapping(path={"/", "/index", "/login"})
public class IndexController {

    @GetMapping()
    public String getIndexPage(Model model, HttpSession sess) {

        // If user has already logged in, going to index will redirect to secret page
        if (sess.getAttribute("loginForm") != null) {
            return "secret";
        }
        
        model.addAttribute("loginForm", new LoginForm());
        
        return "login";
    }
}
