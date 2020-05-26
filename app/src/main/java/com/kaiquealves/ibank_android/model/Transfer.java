package com.kaiquealves.ibank_android.model;

public class Transfer {
    private int outAccount;
    private int incAccount;
    private double value;

    public Transfer(int outAccount, int incAccount, double value) {
        this.outAccount = outAccount;
        this.incAccount = incAccount;
        this.value = value;
    }

    public int getOutAccount() {
        return outAccount;
    }

    public void setOutAccount(int outAccount) {
        this.outAccount = outAccount;
    }

    public int getIncAccount() {
        return incAccount;
    }

    public void setIncAccount(int incAccount) {
        this.incAccount = incAccount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
