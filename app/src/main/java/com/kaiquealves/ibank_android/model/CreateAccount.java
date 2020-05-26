package com.kaiquealves.ibank_android.model;

public class CreateAccount {
    private String owner;
    private String email;
    private String password;

    public CreateAccount(String owner, String email, String password) {
        this.owner = owner;
        this.email = email;
        this.password = password;
    }

    public String getOwner() {
        return owner;
    }

    public void setName(String owner) {
        this.owner = owner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
