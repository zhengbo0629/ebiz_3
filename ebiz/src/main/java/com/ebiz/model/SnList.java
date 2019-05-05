package com.ebiz.model;

public class SnList {
    private Integer id;

    private String SNString;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSNString() {
        return SNString;
    }

    public void setSNString(String SNString) {
        this.SNString = SNString == null ? null : SNString.trim();
    }
}