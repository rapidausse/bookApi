package com.example.bookApi.model.dto;

import jakarta.validation.constraints.NotNull;

public class LoginUserDTO {
    @NotNull(message = "Mail is required")
    public String mail;
    @NotNull(message = "Password is required")
    public String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
