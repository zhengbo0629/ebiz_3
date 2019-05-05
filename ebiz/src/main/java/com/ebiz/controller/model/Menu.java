package com.ebiz.controller.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//一级菜单
public class Menu {
    private String name;
    private String chinese;
    private List<MenuItem> menuItems = new ArrayList<>();

    public Menu() {
    }

    public Menu(String name, String chinese) {
        this.name = name;
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItems(MenuItem... items) {
        Collections.addAll(menuItems, items);
    }

    public void removeMenuItems(MenuItem... items) {
        menuItems.removeAll(Arrays.asList(items));
    }
}
