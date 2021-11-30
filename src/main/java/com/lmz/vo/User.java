package com.lmz.vo;

public class User {
    private String username;
    private String password;
    private Integer permission;
    private Integer age;
    private String phone;
    private Integer stat;

    public User() {
    }

    public User(String username, String password, Integer permission, Integer age, String phone, Integer stat) {
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.age = age;
        this.phone = phone;
        this.stat = stat;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
