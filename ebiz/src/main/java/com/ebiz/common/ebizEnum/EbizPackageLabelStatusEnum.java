package com.ebiz.common.ebizEnum;

/**
 * 发Label
 */
public enum EbizPackageLabelStatusEnum {
    /**
     * 所有用户可见
     */
	UnMadeLabel("UnMade"),
    /**
     * 发label的人领取
     */
	MakingLable("MakingLable"),
    /**
     * 领取完成
     */
	MadeLabel("MadeLabel");
    private final String name;
    EbizPackageLabelStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
