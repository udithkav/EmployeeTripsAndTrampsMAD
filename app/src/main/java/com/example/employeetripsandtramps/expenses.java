package com.example.employeetripsandtramps;

public class expenses {
    private int expenseID;
    private String usern;
    private double amountt;
    private String details;

    public expenses() {
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public void setUsern(String usern) {
        this.usern = usern;
    }

    public void setAmountt(double amountt) {
        this.amountt = amountt;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public String getUsern() {
        return usern;
    }

    public double getAmountt() {
        return amountt;
    }

    public String getDetails() {
        return details;
    }
}


