package com.ebiz.common.ebizEnum;

/*

 */
public enum EbizPackageStatusEnum {
	//PartlyShipped("PartlyShipped"),
	NumberUnMatch("Number UnMatch"),
	UPCUnMatch("UPC UnMatch"),
	UPCNumberUnMatch("UPC and Num UnMatch"),
	Deleted("Deleted"),
	//Placed("Placed"),
	//Confirmed("Confirmed"),
	UnConfirmedInStock("UnConfirmed InStock"),
	UnConfirmedUnReceived("UnConfirmed UnReceived"),
	UnConfirmedUnPlaced("UnConfirmed UnPlaced"),
	//Refused("Refused"),
	InStock("InStock"),
	UnReceived("UnReceived"),
	Packed("Packed"),
	Delivered("Delivered"),
	EmailedLabel("EmailedLabel"),
	/**
	 * 发到仓库
	 */
	Shipped("Shipped"),
	Complete("Complete");

    private final String columnName;

    EbizPackageStatusEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

	public static void main(String[] args) {
//		EbizPackageStatusEnum[] values = EbizPackageStatusEnum.values();
//		for (EbizPackageStatusEnum e:values) {
//			System.out.println("<option>"+e.getColumnName()+"</option>");
//		}
//		EbizPackagePayStatusEnum[] values = EbizPackagePayStatusEnum.values();
//		for (EbizPackagePayStatusEnum e:values) {
//			System.out.println("<option>"+e.getName()+"</option>");
//		}
        EbizStatusEnum[] values = EbizStatusEnum.values();
		for (EbizStatusEnum e:values) {
			System.out.println("<option value=\""+e.getName()+"\">"+e.getChinese()+"</option>");
		}

	}
}
