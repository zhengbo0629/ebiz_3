package com.ebiz.common.ebizEnum;

public enum EbizStatusEnum {
	Active("Active","","激活"),
	UnActive("UnActive","","未激活"),
	LiveDeal("LiveDeal","",""),
	Deleted("Deleted","","");

	
    private final String name;
    private final String group;
    private final String chinese;

    EbizStatusEnum(String name,String group,String chinese) {
        this.name = name;
        this.group=group;
        this.chinese=chinese;
    }

    public String getName() {
        return name;
    }
    public String getGroup(){
    	return group;
    }
    public String getChinese(){
    	return chinese;
    }

}
