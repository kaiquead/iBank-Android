package com.kaiquealves.ibank_android.model;

public class Withdraw {
    private int account;
    private double value;

    public Withdraw(int account, double value) {
        this.account = account;
        this.value = value;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
