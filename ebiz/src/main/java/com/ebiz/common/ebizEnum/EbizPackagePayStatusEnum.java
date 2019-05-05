package com.ebiz.common.ebizEnum;

/**
 * 支付状态
 */
public enum EbizPackagePayStatusEnum {
	Paid("Paid"),
	UnPaid("UnPaid"),
	Paying("Paying"),
	PartlyPaid("PartlyPaid");
    private final String name;

    
    EbizPackagePayStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
