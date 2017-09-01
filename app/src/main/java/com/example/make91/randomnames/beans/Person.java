package com.example.make91.randomnames.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String surname;
    private String gender;
    private String region;
    private int age;
    private String phone;
    private Birthday birthday;
    private String email;
    private String photo;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getRegion() {
        return region;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", region='" + region + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday.toString() +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
