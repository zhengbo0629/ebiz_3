package com.ebiz.common.ebizEnum;

/**
 * 对单中使用
 */
public enum EbizPackageCheckStatusEnum {
    /**
     * 领任务
     */
    UnChecked("UnChecked"),
    /**
     * 领任务中
     */
    Checking("Checking"),
    /**
     * 对单完成
     */
    Checked("Checked");
    private final String name;

    EbizPackageCheckStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
