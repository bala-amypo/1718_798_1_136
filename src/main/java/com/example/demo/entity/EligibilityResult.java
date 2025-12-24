package com.example.demo.service;

public class EligibilityResult {

    private boolean eligible;
    private double approvedAmount;
    private String message;

    public EligibilityResult() {}

    public EligibilityResult(boolean eligible, double approvedAmount, String message) {
        this.eligible = eligible;
        this.approvedAmount = approvedAmount;
        this.message = message;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
