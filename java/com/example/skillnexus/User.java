package com.example.skillnexus;

public class User {
    private String uid;
    private String name;
    private String skill;

    // Empty constructor required for Firestore
    public User() {
    }

    // Full constructor
    public User(String uid, String name, String skill) {
        this.uid = uid;
        this.name = name;
        this.skill = skill;
    }

    // Getters
    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getSkill() {
        return skill;
    }

    // Optional: Setters (only if you need to update object fields manually)
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
