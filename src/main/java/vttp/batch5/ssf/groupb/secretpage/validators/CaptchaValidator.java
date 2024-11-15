package vttp.batch5.ssf.groupb.secretpage.validators;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CaptchaValidator implements ConstraintValidator<CaptchaConstraint, String> {
    
    @Override
    public void initialize(CaptchaConstraint captcha) {
    }

    @Override
    public boolean isValid(String captchaField, ConstraintValidatorContext cxt) {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession sess = attr.getRequest().getSession(true);

        // Get the captcha value from session
        String sessionCaptcha = (String) sess.getAttribute("captcha");

        System.out.printf("\n Session captcha: %s\n", sessionCaptcha);

        return captchaField == null || captchaField.matches(sessionCaptcha);
    }
}

// https://www.baeldung.com/spring-mvc-custom-validator