package com.ebiz.controller.model;

public class Permission {
    private String name;
    private String role;
    private String group;
    private String chinese;

    public Permission(String name, String role, String group, String chinese) {
        this.name = name;
        this.role = role;
        this.group = group;
        this.chinese = chinese;
    }

    public Permission() {
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }
}
