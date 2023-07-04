package com.example.myapplicationtext.module.bean;

public class User {
    private String id;
    //@Column(unique =true)
    private String name;
    private String password;
    private String photo;

    public User() {
    }

    public User(String id, String name, String password, String photo) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.photo = photo;
    }

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
