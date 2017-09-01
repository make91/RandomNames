package com.example.make91.randomnames.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Birthday extends RealmObject {
    private String dmy;

    public String getDmy() {
        return dmy.replaceAll("/", ".");
    }

    @Override
    public String toString() {
        return "'" + getDmy() + '\'';
    }
}
