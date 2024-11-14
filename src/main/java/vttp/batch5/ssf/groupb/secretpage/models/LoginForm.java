package vttp.batch5.ssf.groupb.secretpage.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LoginForm {

    @NotNull(message="Please provide your username")
    @NotEmpty(message="Please provide your username")
    private String username;

    @NotNull(message="Please provide your password")
    @NotEmpty(message="Please provide your password")
    private String password;

    @Override
    public String toString() {
        return "LoginForm [username=" + username + ", password=" + password + "]";
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}