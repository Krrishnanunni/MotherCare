package com.example.mothercare;

public class BabyModel {
    private String name, age, gender, weight, babyId;

    public BabyModel(String name, String age, String gender, String weight, String babyId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.babyId = babyId;
    }

    public String getName() { return name; }
    public String getAge() { return age; }
    public String getGender() { return gender; }
    public String getWeight() { return weight; }
    public String getBabyId() { return babyId; }
}
