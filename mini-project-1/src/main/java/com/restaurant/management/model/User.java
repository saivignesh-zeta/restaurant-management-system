package com.restaurant.management.model;

public class User {

    private int userId;
    private String name;
    private String role; // waiter, manager, admin, kitchen
    private String loginInfo;

    public User() {}

    public User(int userId, String name, String role, String loginInfo) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.loginInfo = loginInfo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(String loginInfo) {
        this.loginInfo = loginInfo;
    }
}
