package com.ebiz.service;

import com.ebiz.common.ebizEnum.EbizUserPermissionEnum;
import com.ebiz.common.ebizEnum.EbizUserTypeEnum;
import com.ebiz.controller.model.Menu;
import com.ebiz.controller.model.Permission;
import com.ebiz.dao.EbizUserMapper;
import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizUser;
import com.ebiz.model.EbizUserExample;
import com.ebiz.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.ebiz.controller.model.MenuItems.*;

/**
 * 用户相关通用逻辑
 */
@Service
public class EBizUserService {
    @Autowired
    private EbizUserMapper ebizUserMapper;

    @Autowired
    private EbizPackageService ebizPackageService;

    public static List<Permission> getAllMenuPermission() {
        List<Permission> permissionList = new ArrayList<>();
        for (EbizUserPermissionEnum e : EbizUserPermissionEnum.values()) {
            if (e.getRole().equals("DoctorDefault")
                    || e.getRole().equals("NurseDefault")
                    || e.getRole().equals("DoctorOption")) {
                Permission permission = new Permission(e.getName(), e.getRole(), e.getGroup(), e.getChinese());
                permissionList.add(permission);
            }
        }
        return permissionList;
    }

    /**
     * 将用户权限字符串转成集合
     *
     * @param permissions 权限字符串
     * @return 权限集合
     */
    public static Set<String> getUserPermissionSet(String permissions) {
        Set<String> permissionSet = new HashSet<>();
        if (StringUtils.isEmpty(permissions)) {
            return permissionSet;
        }
        String[] strings = permissions.split(",");
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].length() > 0) {
                permissionSet.add(strings[i]);
            }
        }
        return permissionSet;
    }

    /**
     * 将用户权限集合转成字符转
     *
     * @param permissionSet 权限集合
     * @return 权限字符串
     */
    public static String getUserPermissionString(Set<String> permissionSet) {
        StringBuilder sb = new StringBuilder();
        for (String permission : permissionSet) {
            if (StringUtils.isNotEmpty(permission)) {
                sb.append(permission).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 将用户权限集合转成字符转
     *
     * @param permissions 权限集合
     * @return 权限字符串
     */
    public static String getUserPermissionString(String[] permissions) {
        StringBuilder sb = new StringBuilder();
        for (String permission : permissions) {
            if (StringUtils.isNotEmpty(permission)) {
                sb.append(permission).append(",");
            }
        }
        return sb.toString();
    }

    public EbizUser get(int id) {
        return ebizUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 用户名 查找用户
     *
     * @param userName 用户
     * @return 用户列表
     */
    public List<EbizUser> getEbizUsersByName(String userName) {
        EbizUserExample example = new EbizUserExample();
        example.createCriteria().andUserNameEqualTo(userName);
        return ebizUserMapper.selectByExample(example);
    }

    /**
     * 用户名查找用户
     *
     * @param userName 用户姓名
     * @return 用户
     */
    public EbizUser getEbizUserByName(String userName) {
        List<EbizUser> ebizUsers = getEbizUsersByName(userName);
        if (ebizUsers.size() > 0) {
            return ebizUsers.get(0);
        }
        return null;
    }

    public void add(EbizUser record) {
        ebizUserMapper.insert(record);
    }

    /**
     * 根据用户类型和权限返回用户菜单
     *
     * @param userType        用户类型
     * @param userPermissions 用户权限
     * @return 菜单
     */
    public List<Menu> getUserMenus(EbizUserTypeEnum userType, String userPermissions, String companyPermissions) {
        boolean isAdministrator = false;
        boolean isDoctor = false;
        boolean isSelfEmployedDoctor = false;
        boolean isNurse = false;
        boolean isOverseaUser = false;
        //如果账户拥有管理员权限
        switch (userType) {
            case Administrator:
                isAdministrator = true;
                break;
            case Doctor:
                isDoctor = true;
                break;
            case Nurse:
                isNurse = true;
                break;
            case SelfEmployedDoctor:
                isSelfEmployedDoctor = true;
                break;
            case Oversea_Buyer:
                isOverseaUser = true;
                break;
        }
        List<Menu> menus = new ArrayList<>();
        //清空菜单
        administrator.getMenuItems().clear();
        systemDealSubscription.getMenuItems().clear();
        dealMarket.getMenuItems().clear();
        companyInfo.getMenuItems().clear();
        companyProduct.getMenuItems().clear();
        nursePackage.getMenuItems().clear();
        packageMenu.getMenuItems().clear();
        overSeaBusiness.getMenuItems().clear();
        wareHouse.getMenuItems().clear();

        //根据权限添加菜单
        if (isAdministrator) {
            menus.add(administrator);
            if (userPermissions.contains(EbizUserPermissionEnum.DoctorAccountManage.getName())) {
                administrator.addMenuItems(accountManage);
            }
            if (userPermissions.contains(EbizUserPermissionEnum.ChargeManage.getName())) {
                administrator.addMenuItems(chargeManage);
            }
        }

        if (isAdministrator || isDoctor || isSelfEmployedDoctor) {
            if (isAdministrator || isDoctor ||
                    userPermissions.contains(EbizUserPermissionEnum.SystemDealSubscriptionManager.getName())) {
                menus.add(systemDealSubscription);
                if (isAdministrator || isDoctor) {
                    systemDealSubscription.addMenuItems(autoEmailDealSubscription);
                }
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.HotProductList.getName())) {
                    systemDealSubscription.addMenuItems(hotProduct);
                }
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.RecommendedProductList.getName())) {
                    systemDealSubscription.addMenuItems(recommendedProduct);
                }
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.AllProductList.getName())) {
                    systemDealSubscription.addMenuItems(allProduct);
                }
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.MyProductSubscriptionList.getName())) {
                    systemDealSubscription.addMenuItems(myProductSubscription);
                }
            }
            if (isAdministrator
                    || userPermissions.contains(EbizUserPermissionEnum.DealMarketManager.getName())) {
                menus.add(dealMarket);
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.MyPublishedDealList.getName())) {
                    dealMarket.addMenuItems(myPublishedDeal);
                }
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.AllPublishedDealList.getName())) {
                    dealMarket.addMenuItems(allPublishedDeal);
                }
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.MyDealPriceInforSetup.getName())) {
                    dealMarket.addMenuItems(myDealPriceInfoSetup);
                }
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.MyDealSubscriptionUserList.getName())) {
                    dealMarket.addMenuItems(myDealSubscriptionUser);
                }
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.SubscriptionMyDealUserList.getName())) {
                    dealMarket.addMenuItems(subscriptionMyDealUser);
                }
                if (isAdministrator || isDoctor ||
                        userPermissions.contains(EbizUserPermissionEnum.MyDealSubscriptionDealList.getName())) {
                    dealMarket.addMenuItems(myDealSubscriptionDeal);
                }
            }
            if (isAdministrator || isDoctor || isSelfEmployedDoctor
                    || userPermissions.contains(EbizUserPermissionEnum.CompanyInforManager.getName())) {
                menus.add(companyInfo);
                if (isAdministrator || isDoctor || isSelfEmployedDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.CompanyInforManage.getName())) {
                    companyInfo.addMenuItems(companyInfoManage);
                }
                if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.CompanyFinancial.getName()))) {
                    companyInfo.addMenuItems(companyFinancial);
                }
                if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.PayCompanyBill.getName()))) {
                    companyInfo.addMenuItems(payCompanyBill);
                }
                if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.UserManage.getName()))) {
                    companyInfo.addMenuItems(userManage);
                }
                if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.DeleteUser.getName()))) {
                    companyInfo.addMenuItems(deleteUser);
                }
                if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.UserAnalysis.getName()))) {
                    companyInfo.addMenuItems(userAnalysis);
                }
            }
            if (isAdministrator || isDoctor || isSelfEmployedDoctor
                    || userPermissions.contains(EbizUserPermissionEnum.CompanyProductManager.getName())) {
                menus.add(companyProduct);
                if (isAdministrator || isDoctor || isSelfEmployedDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.ProductManage.getName())) {
                    companyProduct.addMenuItems(productManage);
                }
                if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.DealManage.getName()))) {
                    companyProduct.addMenuItems(dealManage);
                }
                if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.PackageManage.getName()))) {
                    companyProduct.addMenuItems(packageManage);
                }
                if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.InventoryManage.getName()))) {
                    companyProduct.addMenuItems(inventoryManage);
                }
                if (isAdministrator || isDoctor || isSelfEmployedDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.CheckPackage.getName())) {
                    companyProduct.addMenuItems(checkPackage);
                }
                if (isAdministrator || isDoctor || isSelfEmployedDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.MakeLabel.getName())) {
                    companyProduct.addMenuItems(makeLabel);
                }
                if (isAdministrator || isDoctor || isSelfEmployedDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.PayPackage.getName())) {
                    companyProduct.addMenuItems(payPackage);
                }
                if (!isSelfEmployedDoctor && (isAdministrator || isDoctor
                        || userPermissions.contains(EbizUserPermissionEnum.SendEmail.getName()))) {
                    companyProduct.addMenuItems(sendEmail);
                }
            }

        }
        if (isAdministrator || isDoctor || isSelfEmployedDoctor
                || isNurse || userPermissions.contains(EbizUserPermissionEnum.NursePackageManager.getName())) {
            menus.add(nursePackage);
            nursePackage.addMenuItems(reportPackage, buy, updatePackage, updateCreditCard, packaging);
            if (isAdministrator || isDoctor || isSelfEmployedDoctor
                    || userPermissions.contains(EbizUserPermissionEnum.LiveDeal.getName())) {
                nursePackage.addMenuItems(liveDeal);
            }
            menus.add(packageMenu);
            packageMenu.addMenuItems(allPackage, unConfirmedSellOrOBOPack, unReceivedPac, shippedPack, instockPack,
                    unPaidPac, paidPac, shipCompletePac, unMatchPac);
        }

        if (isAdministrator
                || companyPermissions
                .contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())
                || userPermissions.contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())) {
            menus.add(overSeaBusiness);
            if (isAdministrator || isDoctor
                    || userPermissions.contains(EbizUserPermissionEnum.AcceptPublicTask.getName())) {
                overSeaBusiness.addMenuItems(acceptPublicTask, acceptedTask, completedTask);
            }
            if (isAdministrator || isDoctor || userPermissions
                    .contains(EbizUserPermissionEnum.RecommendedProductsList.getName())) {
                overSeaBusiness.addMenuItems(recommendedProducts, allRcommendedProducts, selfRecommendedProducts);
            }
            if (isAdministrator || isDoctor || isOverseaUser || userPermissions
                    .contains(EbizUserPermissionEnum.CheckRecommendedProductsList.getName())) {
                overSeaBusiness.addMenuItems(checkRecommendedProducts);
            }
            if (isAdministrator || isDoctor || isOverseaUser ||
                    userPermissions.contains(EbizUserPermissionEnum.PublishTask.getName())) {
                overSeaBusiness.addMenuItems(publishTask, allSelfTask, publishedTask, beAcceptedTask, beCompletedTask, paySelfBill);
            }
        }

        if (isAdministrator
                || companyPermissions.contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())) {
            menus.add(wareHouse);
            if (isAdministrator
                    || isDoctor || userPermissions.contains(EbizUserPermissionEnum.ReceivePackage.getName())) {
                wareHouse.addMenuItems(receivePackage);
            }
            if (isAdministrator
                    || isDoctor || userPermissions.contains(EbizUserPermissionEnum.PackPackage.getName())) {
                wareHouse.addMenuItems(packPackage);
            }
            if (isAdministrator
                    || isDoctor || userPermissions.contains(EbizUserPermissionEnum.InventoryManage.getName())) {
                wareHouse.addMenuItems(inventoryManage);
            }
            if (isAdministrator
                    || isDoctor || userPermissions.contains(EbizUserPermissionEnum.WareHouseOrderManage.getName())) {
                wareHouse.addMenuItems(wareHouseOrderManage);
            }
            if (isAdministrator
                    || isDoctor || userPermissions.contains(EbizUserPermissionEnum.BuyerPackageManage.getName())) {
                wareHouse.addMenuItems(buyerPackageManage);
            }
        }
        return menus;
    }

    /**
     * 获取 公司拥有的护士类型
     *
     * @param company 公司
     * @return 用户类型List
     */
    public List<String> getCompanyUserTypes(EbizCompany company) {
        List<String> userTypes = new ArrayList<>();
        for (EbizUserTypeEnum type : EbizUserTypeEnum.values()) {
            if (type.getName().toLowerCase().contains("administrator")) {
                continue;
            }
            if (type.getName().toLowerCase().contains("doctor")) {
                continue;
            }
            if (!company.getPermissions().contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())
                    && type.getName().equals(EbizUserTypeEnum.WarehouseNurse.getName())) {
                continue;
            }
            if (!company.getPermissions().contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())
                    && type.getName().equals(EbizUserTypeEnum.Oversea_Buyer.getName())) {
                continue;
            }
            userTypes.add(type.getName());
        }
        return userTypes;
    }

    /**
     * 获取 该公司用户所有拥有的菜单
     *
     * @param company 公司信息
     * @return 该公司下用户拥有的菜单
     */
    public List<Menu> getCompanyUserMenus(EbizCompany company) {
        List<Menu> menus = new ArrayList<>();
        //清空菜单
        systemDealSubscription.getMenuItems().clear();
        dealMarket.getMenuItems().clear();
        companyInfo.getMenuItems().clear();
        companyProduct.getMenuItems().clear();
        nursePackage.getMenuItems().clear();
        //packageMenu.getMenuItems().clear();
        overSeaBusiness.getMenuItems().clear();
        wareHouse.getMenuItems().clear();

        //系统Deal订阅管理
        if (company.getPermissions().contains(EbizUserPermissionEnum.SystemDealSubscriptionManager.getName())
                || EbizUserPermissionEnum.SystemDealSubscriptionManager.getRole().equals("DoctorDefault")) {
            menus.add(systemDealSubscription);
            if (company.getPermision().contains(EbizUserPermissionEnum.AutoEmailDealSubscription.getName())) {
                systemDealSubscription.addMenuItems(autoEmailDealSubscription);
            }
            systemDealSubscription.addMenuItems(hotProduct, recommendedProduct, allProduct, myProductSubscription);
        }
        //deal 市场管理
        if (company.getPermissions().contains(EbizUserPermissionEnum.DealMarketManager.getName())
                || EbizUserPermissionEnum.DealMarketManager.getRole().equals("DoctorDefault")) {
            menus.add(dealMarket);
            dealMarket.addMenuItems(myPublishedDeal, allPublishedDeal, myDealPriceInfoSetup,
                    myDealSubscriptionUser, subscriptionMyDealUser, myDealSubscriptionDeal);
        }
        //公司信息管理
        if (company.getPermissions().contains(EbizUserPermissionEnum.CompanyInforManager.getName())
                || EbizUserPermissionEnum.CompanyInforManager.getRole().equals("DoctorDefault")) {
            menus.add(companyInfo);
            companyInfo.addMenuItems(companyInfoManage, companyFinancial,
                    payCompanyBill, userManage, deleteUser, userAnalysis);
        }
        //公司产品管理员
        if (company.getPermissions().contains(EbizUserPermissionEnum.CompanyProductManager.getName())
                || EbizUserPermissionEnum.CompanyProductManager.getRole().equals("DoctorDefault")) {
            menus.add(companyProduct);
            if (company.getPermissions().contains(EbizUserPermissionEnum.ProductManage.getName())) {
                companyProduct.addMenuItems(productManage, dealManage, packageManage,
                        inventoryManage, checkPackage, makeLabel, payPackage, sendEmail);
            }
        }
        //任务操作
        if (company.getPermissions().contains(EbizUserPermissionEnum.NursePackageManager.getName())
                || EbizUserPermissionEnum.NursePackageManager.getRole().equals("DoctorDefault")
                || company.getPermissions().contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())
                || EbizUserPermissionEnum.OverSeaBusinessManager.getRole().equals("DoctorDefault")) {
            menus.add(nursePackage);
            nursePackage.addMenuItems(reportPackage, buy, updatePackage, updateCreditCard, packaging, liveDeal);
        }
        //代购业务
        if (company.getPermissions().contains(EbizUserPermissionEnum.OverSeaBusinessManager.getName())
                || EbizUserPermissionEnum.OverSeaBusinessManager.getRole().equals("DoctorDefault")) {
            menus.add(overSeaBusiness);
            overSeaBusiness.addMenuItems(acceptPublicTask, acceptedTask, completedTask,
                    recommendedProducts, allRcommendedProducts, selfRecommendedProducts,
                    checkRecommendedProducts, publishTask, allSelfTask, publishedTask,
                    beAcceptedTask, beCompletedTask, paySelfBill);
        }
        //仓库管理
        if (company.getPermissions().contains(EbizUserPermissionEnum.WareHouseAccountManager.getName())
                || EbizUserPermissionEnum.WareHouseAccountManager.getRole().equals("DoctorDefault")) {
            menus.add(wareHouse);
            wareHouse.addMenuItems(receivePackage, packPackage, wareHouseOrderManage, buyerPackageManage);
        }
        return menus;
    }

    //得到所有的已经激活护士的名称，对于该公司而言
    public HashMap<String, String> readAllActiveNurseNameEmailForCompany(String companyName) {
        HashMap<String, String> userNameEmailMap = new HashMap<>();
        EbizUserExample example = new EbizUserExample();
        EbizUserExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("Active");
        criteria.andCompanyNameEqualTo(companyName);
        List<String> userTypeList = new ArrayList<>();
        userTypeList.add(EbizUserTypeEnum.Nurse.getName());
        userTypeList.add(EbizUserTypeEnum.TrustedNurse.getName());
        criteria.andUserTypeIn(userTypeList);
        example.setOrderByClause("id");
        List<EbizUser> list = ebizUserMapper.selectByExample(example);
        for (EbizUser user : list) {
            userNameEmailMap.put(user.getUserName(), user.getEmail());
        }
        return userNameEmailMap;
    }

    //最近两个月注册的护士对于本公司
    public List<String> readActiveNurseEmailRegistratedInlastTwoMonthForCompany(String companyName) {
        List<String> emaiList = new ArrayList<>();
        EbizUserExample example = new EbizUserExample();
        EbizUserExample.Criteria criteria = example.createCriteria();

        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -2);  //设置为前3月
        dBefore = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间
        System.out.println("两个月之前时间=======" + defaultStartDate);

        criteria.andCreateTimeGreaterThan(defaultStartDate);
        criteria.andStatusEqualTo("Active");
        criteria.andCompanyNameEqualTo(companyName);
        criteria.andUserNameEqualTo(EbizUserTypeEnum.Nurse.getName());
        example.setOrderByClause("id");
        List<EbizUser> list = ebizUserMapper.selectByExample(example);
        for (EbizUser user : list) {
            emaiList.add(user.getEmail());
        }
        return emaiList;
    }

    //读取包裹用户名称对于该公司在最近两个月的时间
    public List<String> readPackagesUserNameInLastTwoMonthForCompany(String companyName) {
        List<String> emaiList = new ArrayList<>();
        EbizUserExample example = new EbizUserExample();
        EbizUserExample.Criteria criteria = example.createCriteria();

        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -2);  //设置为前3月
        dBefore = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间
        System.out.println("两个月之前时间=======" + defaultStartDate);

        criteria.andCreateTimeGreaterThan(defaultStartDate);
        criteria.andStatusNotEqualTo("Deleted");
        criteria.andCompanyNameEqualTo(companyName);
        example.setOrderByClause("id");
        List<EbizUser> list = ebizUserMapper.selectByExample(example);
        for (EbizUser user : list) {
            emaiList.add(user.getEmail());
        }
        return emaiList;
    }

    //读取包裹用户名称对于该公司在最近两个月的时间。但是必须领取的包裹数量必须在5个以上
    public List<String> readMoreThanFivePackagesUserNameInLastTwoMonthForCompany(String companyName) {
        List<String> userNames = new ArrayList<>();
        Set<String> userSets = new HashSet<>();
        userNames = ebizPackageService.getUserNameByCountPackage(companyName, 5, 2);
        userSets.addAll(userNames);
        userNames.clear();
        userNames.addAll(userSets);
        EbizUserExample ebizUserExample = new EbizUserExample();
        EbizUserExample.Criteria criteria = ebizUserExample.createCriteria();
        criteria.andUserNameIn(userNames);
        List<EbizUser> list = new ArrayList<>();
        list = ebizUserMapper.selectByExample(ebizUserExample);
        List<String> emailAddress = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (EbizUser user : list) {
                emailAddress.add(user.getEmail());
            }
        }
        return emailAddress;
    }

    //读取包裹用户名称对于该公司在最近两个月的时间。但是必须领取的包裹数量必须在10个以上
    public List<String> readMoreThanTenPackagesUserNameInLastTwoMonthForCompany(String companyName) {
        List<String> userNames = new ArrayList<>();
        Set<String> userSets = new HashSet<>();
        userNames = ebizPackageService.getUserNameByCountPackage(companyName, 10, 2);
        userSets.addAll(userNames);
        userNames.clear();
        userNames.addAll(userSets);
        EbizUserExample ebizUserExample = new EbizUserExample();
        EbizUserExample.Criteria criteria = ebizUserExample.createCriteria();
        criteria.andUserNameIn(userNames);
        List<EbizUser> list = new ArrayList<>();
        list = ebizUserMapper.selectByExample(ebizUserExample);
        List<String> emailAddress = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (EbizUser user : list) {
                emailAddress.add(user.getEmail());
            }
        }
        return emailAddress;
    }

    /**
     * 信任用户
     *
     * @param companyName
     * @return
     */
    public List<String> readAllActiveTrustedNurseNameEmailForCompany(String companyName) {
        EbizUserExample example = new EbizUserExample();
        EbizUserExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("Active");
        criteria.andCompanyNameEqualTo(companyName);
        criteria.andUserTypeEqualTo(EbizUserTypeEnum.TrustedNurse.getName());
        List<EbizUser> list = new ArrayList<>();
        list = ebizUserMapper.selectByExample(example);
        List<String> emailAddress = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (EbizUser user : list) {
                emailAddress.add(user.getEmail());
            }
        }
        return emailAddress;
    }

    /**
     * 非信任用户
     *
     * @param companyName
     * @return
     */
    public List<String> readAllActiveUnTrustedNurseNameEmailForCompany(String companyName) {
        EbizUserExample example = new EbizUserExample();
        EbizUserExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("Active");
        criteria.andCompanyNameEqualTo(companyName);
        criteria.andUserTypeEqualTo(EbizUserTypeEnum.UnTrustedNurse.getName());
        List<EbizUser> list = new ArrayList<>();
        list = ebizUserMapper.selectByExample(example);
        List<String> emailAddress = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (EbizUser user : list) {
                emailAddress.add(user.getEmail());
            }
        }
        return emailAddress;
    }
}
