package com.example.mothercare;

public class DietModel {
    private String dietDetails;
    private String date;

    public DietModel(String dietDetails, String date) {
        this.dietDetails = dietDetails;
        this.date = date;
    }

    public String getDietDetails() {
        return dietDetails;
    }

    public String getDate() {
        return date;
    }
}
