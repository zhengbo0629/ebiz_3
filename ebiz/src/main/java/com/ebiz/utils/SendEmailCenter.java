package com.ebiz.utils;

import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizUser;
import com.ebiz.model.ProductList;

import java.util.HashSet;
import java.util.List;

/**
 * 该类主要用于包装emailAddress
 */
public class SendEmailCenter {

    public static boolean sendDealEmail(ProductList product, EbizUser currentUser, EbizCompany currentCompany, String[] addressName, List<String> emailList, String emailContent) {
        System.out.println(addressName.toString());
        HashSet<String> addressSet = new HashSet<>();
        for (int i = 0; i < addressName.length; i++) {
            addressSet.add(addressName[i]);
            System.out.println(addressName[i]);
        }
        ProductList tempEbizProduct = product;
        if (tempEbizProduct == null)
            return false;
        String eastebizlinkString = "<a href=http://eastebiz.com/>www.eastebiz.com</a> <br>";

        String emailContentString = "Dear All: \n";
        emailContentString = emailContentString + "Product we want:\n";

        String[] uris = tempEbizProduct.getURI().split("\n");
        String uuuString = "<a href=" + uris[0] + ">" + tempEbizProduct.getProductName() + "</a> <br>";
        if (uris.length > 1) {
            for (int i = 1; i < uris.length; i++) {
                uuuString = uuuString + "or<br>";
                if (uris[i].length() >= 5) {
                    uuuString = uuuString + "<a href=" + uris[i] + ">" + tempEbizProduct.getProductName() + "</a> <br>";
                } else {
                    uuuString = uuuString + tempEbizProduct.getProductName() + "</a> <br>";
                }

            }
        }
        String contentfromPanel = emailContent;
        emailContentString = emailContentString + uuuString;
        emailContentString = emailContentString + "\n";
        contentfromPanel = contentfromPanel.replace("\n\n", "");
        if (!contentfromPanel.endsWith("\n")) {
            contentfromPanel = contentfromPanel + "\n";
        }
        if (contentfromPanel.length() > 5) {
            emailContentString = emailContentString + contentfromPanel + "\n";
        }
        String shippingpreString = "Accept order shipped to ";
        boolean tt = false;
        if (addressSet.contains("Home")) {
            tt = true;
            shippingpreString = shippingpreString + "Home ";
        }
        if (addressSet.contains(currentCompany.getAddressDetail1()) && currentCompany.getAddressDetail1().length() > 0) {
            if (tt) {
                shippingpreString = shippingpreString + ",";
            }
            tt = true;
            shippingpreString = shippingpreString + currentCompany.getAddressDetail1() + " ";
        }
        if (addressSet.contains(currentCompany.getAddressDetail2()) && currentCompany.getAddressDetail2().length() > 0) {
            if (tt) {
                shippingpreString = shippingpreString + ",";
            }
            tt = true;
            shippingpreString = shippingpreString + currentCompany.getAddressDetail2() + " ";
        }
        if (addressSet.contains(currentCompany.getAddressDetail3()) && currentCompany.getAddressDetail3().length() > 0) {
            if (tt) {
                shippingpreString = shippingpreString + ",";
            }
            tt = true;
            shippingpreString = shippingpreString + currentCompany.getAddressDetail3() + " ";
        }
        if (shippingpreString.endsWith(" ")) {
            shippingpreString = shippingpreString.substring(0, shippingpreString.length() - 1);
        }

        emailContentString = emailContentString + shippingpreString + ".\n";

        if (addressSet.contains("Home")) {

            if (tempEbizProduct.getPrice() == tempEbizProduct.getPromotPrice()) {
                emailContentString = emailContentString + "Ship to home price $" + tempEbizProduct.getPrice() + "\n";
            } else {
                emailContentString = emailContentString + " Ship to home price $" + tempEbizProduct.getPrice();
                emailContentString = emailContentString + " and promotional price $" + tempEbizProduct.getPromotPrice()
                        + " for " + tempEbizProduct.getPromotQuantity() + " or more Units.\n";
            }
        }
        boolean haswareHouseAddress = addressSet.contains(currentCompany.getAddressName1())
                && currentCompany.getAddressName1().length() > 0
                || addressSet.contains(currentCompany.getAddressName2()) && currentCompany.getAddressName2().length() > 0
                || addressSet.contains(currentCompany.getAddressName3()) && currentCompany.getAddressName3().length() > 0;
        if (haswareHouseAddress) {
            if (tempEbizProduct.getWarehousePrice() == tempEbizProduct.getWarehousePromotePrice()) {
                emailContentString = emailContentString + "Ship to warehouse price $" + tempEbizProduct.getWarehousePrice()
                        + "\n";
            } else {
                emailContentString = emailContentString + "Ship to warehouse price $" + tempEbizProduct.getWarehousePrice();
                emailContentString = emailContentString + " and promotional price $"
                        + tempEbizProduct.getWarehousePromotePrice() + " for " + tempEbizProduct.getWarehousePromotePrice()
                        + " or more units.\n";
            }
        }
        emailContentString = emailContentString + "\n";
        emailContentString = emailContentString + "Please get tickets or report your products on\n";
        emailContentString = emailContentString + eastebizlinkString;
        emailContentString = emailContentString + "\n";

        String warehouseAddressPreString = "WareHouse address:\n";

        if (haswareHouseAddress) {
            emailContentString = emailContentString + warehouseAddressPreString;
        }
        boolean temp = false;
        if (addressSet.contains(currentCompany.getAddressName1()) && currentCompany.getAddressName1().length() > 0) {
            emailContentString = emailContentString + currentCompany.getAddressName1() + ":\n" + currentCompany.getAddressDetail1();
            temp = true;
        }
        if (addressSet.contains(currentCompany.getAddressName2()) && currentCompany.getAddressName2().length() > 0) {
            if (temp) {
                emailContentString = emailContentString + "\nOr ";
            }
            emailContentString = emailContentString + currentCompany.getAddressName2() + ":\n" + currentCompany.getAddressDetail2();
            temp = true;
        }

        if (addressSet.contains(currentCompany.getAddressName3()) && currentCompany.getAddressName3().length() > 0) {
            if (temp) {
                emailContentString = emailContentString + "\nOr ";
            }
            emailContentString = emailContentString + currentCompany.getAddressName2() + ":\n" + currentCompany.getAddressDetail2();
            temp = true;
        }
        emailContentString = emailContentString + "\n";
        emailContentString = emailContentString + "Regards\n" + currentUser.getUserName()+"("+currentCompany.getCompanyName()+")" + "\n";

        emailContentString = emailContentString.replace("\n", "<br>");
        if (emailList == null) {
            return false;
        }
        // System.out.println(emailAddressList.size());
        // if(true){
        // return;
        // }

        String emailTitleString = "We Want: " + tempEbizProduct.getProductName();

        if (emailList == null || emailList.size() == 0) {
            return false;
        }

        //emailList.clear();
        ///emailList.add("miketian668@gmail.com");
        //emailList.add("tgxfff@hotmail.com");
        return SendEmailUtil.sendEmailtoMultipleReciForCompany(currentCompany.getEmail() , currentCompany.getEmailPassword() , emailList, emailTitleString,
                emailContentString, null);
    }

}
