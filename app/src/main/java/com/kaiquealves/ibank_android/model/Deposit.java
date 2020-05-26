package com.kaiquealves.ibank_android.model;

public class Deposit {
    private int account;
    private Double value;

    public Deposit(int account, Double value) {
        this.account = account;
        this.value = value;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
