package org.example.api;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotEmpty;
import java.beans.ConstructorProperties;

public class User {

    int id;
    @NotEmpty private final String first_name;
    @NotEmpty private final String username;
    @NotEmpty private final String last_name;
    private final String email;

    @JsonCreator
    @ConstructorProperties({"id", "first_name", "username", "last_name", "email"})
    public User(int id, String first_name, String username, String last_name, String email) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getUsername() {
        return username;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }
}
