package com.ebiz.controller.model;

/**
 * 页面菜单项
 * 二级菜单
 */
public class MenuItem {
    /**
     * 控制器具体方法的URL
     */
    private String link;
    /**
     * 菜单项英文名
     */
    private String name;
    /**
     * 菜单项中文名
     */
    private String chinese;


    public MenuItem() {
    }

    public MenuItem(String link, String name, String chinese) {
        this.link = link;
        this.name = name;
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
