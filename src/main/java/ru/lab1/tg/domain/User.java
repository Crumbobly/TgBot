package ru.lab1.tg.domain;

public class User {

    private final long userId;
    private final String email;
    private final String name;

    public User(String name, String email, long userId){
        this.name = name;
        this.email = email;
        this.userId = userId;
    }
}
