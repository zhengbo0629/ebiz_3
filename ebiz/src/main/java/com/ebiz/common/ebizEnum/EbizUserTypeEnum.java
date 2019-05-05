package com.ebiz.common.ebizEnum;

/**
 * 用户类型
 */
public enum EbizUserTypeEnum {
    Administrator("Administrator"),//系统管理员
    Doctor("Doctor"), //医生，允许管理护士；发单给护士等等
    Nurse("Nurse"),//允许给医生报货。
    TrustedNurse("TrustedNurse"),//可能收到医生的deal邮件；
    UnTrustedNurse("UnTrustedNurse"),//收不到医生的deal邮件；
    SelfEmployedDoctor("SelfEmployedDoctor"),//自我雇佣的医生，独立医生，没有管理护士的权限，独立账号；
    Oversea_Buyer("OverseaBuyer"),//海外买家
    WarehouseNurse("WarehouseNurse");//仓库管理

    private final String name;

    EbizUserTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
