package com.ebiz.controller.model;
/*
* 定义菜单常量值
* */
public class MenuItems {
    public static Menu administrator = new Menu("System Manager", "网站业务");
    public static final MenuItem accountManage
            = new MenuItem("/view/SystemManage/doctorUserManage.html", "Master Account Manage", "主账号管理"); //可以添加，编辑，删除主账号
    public static final MenuItem chargeManage
            = new MenuItem("/view/SystemManage/doctorUserBalanceManage.html", "Charge Manage", "收费管理"); //扣费管理

    public static Menu systemDealSubscription = new Menu("System Deal Subscription Manager", "系统Deal订阅");
    public static final MenuItem autoEmailDealSubscription
            = new MenuItem("", "Auto Email Deal Subscription", "系统推荐deal");//网络爬虫爬出来的deal自动群发邮件订阅，收费项目，医生要额外缴纳费用才会开通                      网络管理员给医生,医生才能给护士。医生(公司)没有了，护士也无法用
    public static final MenuItem hotProduct
            = new MenuItem("/view/SystemDealSubscription/liveDeal.html", "Hot Product", "热门产品");//当前热门产品，在页面上可以设置价格点击订阅，医生自动拥有，护士需要被授权才能看到
    public static final MenuItem recommendedProduct
            = new MenuItem("", "Recommended Product", "系统推荐产品");//系统推荐的产品，在页面上可以设置价格点击订阅，医生自动拥有，护士需要被授权才能看到
    public static final MenuItem allProduct
            = new MenuItem("", "All Product", "所有产品");//所有产品list，在页面上可以搜索关键词过滤，然后设置价格点击订阅，医生自动拥有，护士需要被授权才能看到
    public static final MenuItem myProductSubscription
            = new MenuItem("", "My Product Subscription", "我的订阅产品");//我的订阅清单,设定了价格后点击subscription. 当价格低于设定价格时候系统自动发送邮件给订阅者；医生自动拥有，护士需要被授权才能看到

    //deal 市场管理，医生也看不到这个，需要administrator的授权才能看到，这个是额外的服务项目        网络管理员给医生 医生才能给护士，公司欠费自动停
    public static Menu dealMarket = new Menu("Deal Market Manager", "Deal市场");
    public static final MenuItem myPublishedDeal =
            new MenuItem("", "My Published Deal List", "我的deal管理");//我订阅的产品清单，可以添加修改价格和删除
    public static final MenuItem allPublishedDeal
            = new MenuItem("", "All Published Deal ", "公共deal(2天延迟)");//所有公开发布的deal(都是延迟两天的deal)，可以在这里进行查阅；看到某个人发布的deal比较好就可以订阅
    public static final MenuItem myDealPriceInfoSetup
            = new MenuItem("", "My Deal Price Infor Setup", "我的deal订阅设置");//我的deal的价格信息的设定，所有订阅我的deal的人每个月都要按照这个价格缴费
    public static final MenuItem myDealSubscriptionUser
            = new MenuItem("", "My Deal Subscription User ", "我订阅的用户"); //我都订阅了谁的deal，每个月多少钱等等列表
    public static final MenuItem subscriptionMyDealUser
            = new MenuItem("", "Subscription My Deal User ", "订阅我的用户"); //都是谁订阅了我的deal，每个月收入多少钱
    public static final MenuItem myDealSubscriptionDeal
            = new MenuItem("", "My Deal Subscription Deal ", "我订阅的deal"); //我订阅的所有的人发布的所有的deal

    /**
     * 公司信息管理员 不建议给护士或者秘书，医生自己享有这个管理权限就好了
     */
    public static Menu companyInfo = new Menu("Company Information Manager", "公司信息");
    //公司信息管理员 不建议给护士或者秘书，医生自己享有这个管理权限就好了
    //NurseExclude means 医生无法将此权限给护士，护士的用户管理里面也无法管理这些权限
    public static final MenuItem companyInfoManage
            = new MenuItem("/view/CompanyInfo/companyAccountSetting.html", "Company Information Manage", "公司账户管理"); //公司管理，可以修改公司资料，护士不允许有这个权限
    public static final MenuItem companyFinancial
            = new MenuItem("/view/CompanyInfo/companyFinancial.html", "Company Financial", "公司财务"); //公司账务查询
    public static final MenuItem payCompanyBill
            = new MenuItem("/view/CompanyInfo/payCompanyBill.html", "Pay Company Bill", "公司账户充值"); //支付公司账单
    public static final MenuItem userManage
            = new MenuItem("/view/CompanyInfo/userManage.html", "User Manage", "用户管理");//拥有此权限可看到子账号list，可以添加编辑用户和修改用户权限；
    public static final MenuItem deleteUser
            = new MenuItem("/view/CompanyInfo/deleteUser.html", "Delete User", "删除用户");//拥有此权限可以删除用户
    public static final MenuItem userAnalysis
            = new MenuItem("/view/CompanyInfo/userAnalysis.html", "User Analysis", "用户分析");//拥有此权限可以有用户分析模块，看到用户的删除的单，未来开发

    /**
     * 公司产品管理员
     */
    public static Menu companyProduct =
            new Menu("Company Product Manager", "公司产品");
    public static final MenuItem productManage =
            new MenuItem("/view/EbizProduct/ProductManager.html", "Manage Product", "产品管理");//拥有此权限可以看到产品list，可以添加修改删除产品；
    public static final MenuItem dealManage =
            new MenuItem("/view/EbizDeal/DealManager.html", "Deal Manage", "Deal管理");//拥有此权限可以群发deal给护士，添加修改删除deal
    public static final MenuItem packageManage =
            new MenuItem("/view/EbizPackage/list.html", "Package Manage", "包裹管理");//拥有此权限可以看到修改所有护士的所有包裹
    public static final MenuItem inventoryManage =
            new MenuItem("/view/EbizProduct/StockManager.html", "Inventory Manage", "库存管理");//拥有此权限可以查看产品的库存状况
    //这四个功能要放在同一张表格上面，更方便操作，具有权限的就显示，不具有权限的就隐藏
    public static final MenuItem checkPackage =
            new MenuItem("/view/EbizPackage/allUnCheckedPackForCompany.html", "Check Package", "对单");
    public static final MenuItem makeLabel =
            new MenuItem("/view/EbizPackage/allUnLabeledPackForCompany.html", "Make Label", "发送Lable"); //拥有此权限可以制作label并给护士email label
    public static final MenuItem payPackage =
            new MenuItem("/view/EbizPackage/allUnPaidPackForCompany.html", "Pay Package", "支付用户");//拥有此权限可以支付护士钱，并且给护士发支付确认邮件
    public static final MenuItem sendEmail =
            new MenuItem("/view/EbizEmail/sendEmail.html", "Send Email", "发邮件");//拥有此权限可以通过系统发邮件；

    /**
     * 护士权限 医生免费获得这个权限，医生赋予子账号这个权限，
     * 子账号才能看到下面的4个权限中被赋予的权限；
     */
    public static Menu nursePackage =
            new Menu("Nurse Package Manager", "任务操作");
    public static final MenuItem reportPackage =
            new MenuItem("/view/EbizPackage/reportPackage.html", "Report Package", "领票/预报");//拥有此权限汇报并且管理自己的package，给护士此权限，
    public static final MenuItem buy =
            new MenuItem("/view/EbizPackage/sellorobo.html", "Buy / Bargaining", "求收购/议价");
    public static final MenuItem updatePackage =
            new MenuItem("/view/EbizPackage/unReceivedPacAndUnConfirmed.html", "Update Package", "更新包裹信息");
    public static final MenuItem updateCreditCard =
            new MenuItem("/view/EbizPackage/unPaidPac.html", "Update Credit Card", "更新信用卡信息");
    public static final MenuItem packaging =
            new MenuItem("/view/EbizPackage/packingPac.html", "Packaging", "打包包裹");
    public static final MenuItem liveDeal =
            new MenuItem("/view/EbizDeal/livingDeal.html", "Live Deal", "正在收购");//医生拥有这个权限，护士需要从医生那里获得这个权限才能看到 live deal，

    public static Menu packageMenu = new Menu("View Package", "查看包裹");
    public static final MenuItem allPackage = new MenuItem("/view/EbizPackage/packageManager.html", "allPackage", "所有包裹");
    public static final MenuItem unConfirmedSellOrOBOPack = new MenuItem("/view/EbizPackage/unConfirmedSellOrOBOPack.html", "unConfirmedSellOrOBOPack", "未确认包裹");
    public static final MenuItem unReceivedPac = new MenuItem("/view/EbizPackage/unReceivedPac.html", "unReceivedPac", "未发货包裹");
    public static final MenuItem shippedPack = new MenuItem("/view/EbizPackage/shippedPack.html", "shippedPack", "途中包裹");
    public static final MenuItem instockPack = new MenuItem("/view/EbizPackage/instockPack.html", "instockPack", "在家包裹");
    public static final MenuItem unPaidPac = new MenuItem("/view/EbizPackage/unPaidPac.html", "unPaidPac", "待结算包裹");
    public static final MenuItem paidPac = new MenuItem("/view/EbizPackage/paidPac.html", "paidPac", "已结算包裹");
    public static final MenuItem shipCompletePac = new MenuItem("/view/EbizPackage/shipCompletedPac.html", "shipCompletedPac", "邮寄完成包裹");
    public static final MenuItem unMatchPac = new MenuItem("/view/EbizPackage/unMatchPac.html", "unMatchPac", "不匹配包裹");

    /**
     * 海外买家用户权限 医生需要获得这个权限后，才能开通代购生意，
     * 才能赋予自己的护士下面的两个权限，也才能接受海外用户的注册
     * 网络管理员给医生 医生才能给护士，公司欠费自动停
     */
    public static Menu overSeaBusiness = new Menu("WareHouse Account Manager", "代购业务");//拥有此权限可以对单，确认货物数量和种类无误后修改订单状态
    public static final MenuItem acceptPublicTask = new MenuItem("/view/AgencyTask/AcceptPublicTask.html", "Accept Public Task", "接受任务(任务市场)");//护士需要从医生那里获得这个权限，拥有此权限能看到当前的公开任务清单，可以接受当前的公开任务；
    public static final MenuItem acceptedTask = new MenuItem("/view/AgencyTask/AcceptedTask.html", "Accepted Task", "已接受任务");
    public static final MenuItem completedTask = new MenuItem("/view/AgencyTask/CompletedTask.html", "Completed Task", "已完成任务");
    public static final MenuItem recommendedProducts = new MenuItem("/view/AgencyTask/RecommendedProductsManage.html", "Recommended Products Manage", "推荐产品管理");//护士需要从医生那里获得这个权限，拥有此权限的护士可以推荐添加修改删除自己的推荐产品
    public static final MenuItem allRcommendedProducts = new MenuItem("/view/AgencyTask/AllRecommendedProducts.html", "All Recommended Products ", "所有推荐的产品");
    public static final MenuItem selfRecommendedProducts = new MenuItem("/view/AgencyTask/SelfRecommendedProducts.html", "Self Recommended Products ", "自己推荐的产品");

    //海外买家用户权限，注册over sea buyer 账号自动获得下面两个权限
    public static final MenuItem checkRecommendedProducts = new MenuItem("", "Check Recommended Products ", "查看推荐产品");//查看公司范围的所有推荐产品，可以直接接受推荐产品，任务直接发给推荐人。
    public static final MenuItem publishTask = new MenuItem("/view/AgencyTask/PublishTask.html", "Publish Task", "发布任务");//拥有此权限的人可以发布公开任务
    public static final MenuItem allSelfTask = new MenuItem("/view/AgencyTask/AllPublishTask.html", "All My Publish Task", "所有个人任务");
    public static final MenuItem publishedTask = new MenuItem("/view/AgencyTask/PublishedTask.html", "Published Task", "已发布的任务");
    public static final MenuItem beAcceptedTask = new MenuItem("/view/AgencyTask/BeAcceptedTask.html", "Be Accepted Task", "已被接受任务");
    public static final MenuItem beCompletedTask = new MenuItem("/view/AgencyTask/BeCompletedTask.html", "Be Completed Task", "已被完成任务");
    public static final MenuItem paySelfBill = new MenuItem("/view/AgencyTask/PayCompanyBill.html", "Pay Company Bill", "个人账户充值");

    /**
     * 仓库管理人员权限
     * 医生赋予子账号这个权限，子账号才能看到下面的5个权限中被赋予的权限；
     * 网络管理员给医生 医生才能给护士，公司欠费自动停
     */
    public static Menu wareHouse = new Menu("WareHouse Account Manager", "仓库");
    public static final MenuItem receivePackage = new MenuItem("", "Receive Package", "接受包裹");//拥有此权限可以接受包裹，未来给仓库员工使用
    public static final MenuItem packPackage = new MenuItem("", "Pack Package", "打包");//拥有此权限可以打包，未来给仓库工人此权限
    public static final MenuItem warehouseInventoryManage = new MenuItem("", "Warehouse Inventory Manage", "库存管理");//拥有此权限可以查看管理库存，并且可以批量发送货物
    public static final MenuItem wareHouseOrderManage = new MenuItem("", "WareHouse Order Manage", "仓库订单管理");//拥有此权限可以管理仓库发货，假设有自己的网站出售产品。。。。。。。。
    public static final MenuItem buyerPackageManage = new MenuItem("", "Buyer Package Manage", "仓库包裹管理");//拥有此权限可以管理买家的包裹，一单好几个包裹什么的都在此生成
}
