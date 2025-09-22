package com.example.mothercare;

public class Doctor {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String qualification;
    private String loginId;

    public Doctor(String id, String name, String email, String phone, String qualification, String loginId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.qualification = qualification;
        this.loginId = loginId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getQualification() {
        return qualification;
    }

    public String getLoginId() {
        return loginId;
    }
}
