package com.example.demo.dto;

public class LoanDtos {
    
    public static class LoanRequestDto {
        private Double requestedAmount;
        private Integer tenureMonths;
        private String purpose;
        
        // No-arg constructor
        public LoanRequestDto() {}
        
        // Constructor with fields
        public LoanRequestDto(Double requestedAmount, Integer tenureMonths, String purpose) {
            this.requestedAmount = requestedAmount;
            this.tenureMonths = tenureMonths;
            this.purpose = purpose;
        }
        
        // Getters and setters
        public Double getRequestedAmount() { return requestedAmount; }
        public void setRequestedAmount(Double requestedAmount) { this.requestedAmount = requestedAmount; }
        public Integer getTenureMonths() { return tenureMonths; }
        public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }
        public String getPurpose() { return purpose; }
        public void setPurpose(String purpose) { this.purpose = purpose; }
    }
    
    public static class FinancialProfileDto {
        private Double monthlyIncome;
        private Double monthlyExpenses;
        private Double existingLoanEmi;
        private Integer creditScore;
        private Double savingsBalance;
        
        // No-arg constructor
        public FinancialProfileDto() {}
        
        // Constructor with fields
        public FinancialProfileDto(Double monthlyIncome, Double monthlyExpenses, Double existingLoanEmi, 
                                  Integer creditScore, Double savingsBalance) {
            this.monthlyIncome = monthlyIncome;
            this.monthlyExpenses = monthlyExpenses;
            this.existingLoanEmi = existingLoanEmi;
            this.creditScore = creditScore;
            this.savingsBalance = savingsBalance;
        }
        
        // Getters and setters
        public Double getMonthlyIncome() { return monthlyIncome; }
        public void setMonthlyIncome(Double monthlyIncome) { this.monthlyIncome = monthlyIncome; }
        public Double getMonthlyExpenses() { return monthlyExpenses; }
        public void setMonthlyExpenses(Double monthlyExpenses) { this.monthlyExpenses = monthlyExpenses; }
        public Double getExistingLoanEmi() { return existingLoanEmi; }
        public void setExistingLoanEmi(Double existingLoanEmi) { this.existingLoanEmi = existingLoanEmi; }
        public Integer getCreditScore() { return creditScore; }
        public void setCreditScore(Integer creditScore) { this.creditScore = creditScore; }
        public Double getSavingsBalance() { return savingsBalance; }
        public void setSavingsBalance(Double savingsBalance) { this.savingsBalance = savingsBalance; }
    }
}