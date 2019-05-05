package com.ebiz.common.ebizEnum;

/**
 * 产品状态
 */
public enum ProductConditionColumnEnum {
	New("New"),
    UsedLikeNew("Used LikeNew"),
    UsedGood("Used Good"),
    Used("Used"),
    Refurbished("Refurbished"),
    WareHouseDamage("Warehouse Damaged"),
    Disposed("Disposed");


    private final String columnName;

    ProductConditionColumnEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
    public String toString(){
    	return columnName;
    }
}
