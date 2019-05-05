package com.ebiz.service;


import com.ebiz.bo.PackageListBo;
import com.ebiz.common.ebizEnum.*;
import com.ebiz.dao.OperationRecordMapper;
import com.ebiz.dao.PackageListMapper;
import com.ebiz.dao.ProductListMapper;
import com.ebiz.model.*;
import com.ebiz.utils.GeneralMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class PackageListService {

    @Autowired
    private PackageListMapper packageListMapper;

    @Autowired
    private OperationRecordMapper operationRecordMapper;

    @Autowired
    private ProductListMapper productListMapper;


    //去往当前已经领取但是还未完成的任务，在该页面可以取消任务（批量），可以修改任务的状态和删除任务
    public Map<String, Object> readCheckingTasksForCompany(EbizUser currentUser, EbizCompany currentCompany, String currentPage, String pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andCheckStatusEqualTo(EbizPackageCheckStatusEnum.Checking.getName());
        criteria.andCheckerEqualTo(currentUser.getUserName());
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));//开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //去往对单管理里面的所完成的任务的管理页面所需要的页面数据
    public Map<String, Object> readAllCheckedPackagesForUser(EbizUser currentUser, EbizCompany currentCompany, String currentPage, String pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andCheckerEqualTo(currentUser.getUserName());
        criteria.andCheckStatusEqualTo("Checked");
        criteria.andCompanyNameEqualTo(currentCompany.getCompanyName());
        List<String> listStr = new ArrayList<>();
        listStr.add("Delivered");
        listStr.add("EmailedLabel");
        listStr.add("Shipped");
        criteria.andStatusNotIn(listStr);
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));//开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }


    //进入到对单页面需要查找的数据
    public Map<String, Object> findAllUnCheckedPackForCompany(String companyName, String currentPage, String pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andStatusEqualTo("Delivered");
        criteria1.andCompanyNameEqualTo(companyName);
        example.or(criteria1);

        PackageListExample.Criteria criteria2 = example.createCriteria();
        criteria2.andStatusEqualTo("EmailedLabel");
        criteria2.andCompanyNameEqualTo(companyName);
        example.or(criteria2);

        PackageListExample.Criteria criteria3 = example.createCriteria();
        criteria3.andStatusEqualTo("Shipped");
        criteria3.andCompanyNameEqualTo(companyName);
        example.or(criteria3);

        //执行得到结果
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));//开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //进入到当前正在进行的label
    public Map<String, Object> readMakingLabelTasksForCompany(EbizUser currentUser, EbizCompany currentCompany, String currentPage, String pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andLabelStatusEqualTo(EbizPackageLabelStatusEnum.MakingLable.getName());
        criteria.andLabelerEqualTo(currentUser.getUserName());

        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));//开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //通过id集合查找集合对象
    public List<PackageList> findPackages(List<Integer> uidList) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(uidList);
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        return list;
    }

    //取消任务（Label）
    public boolean cancelLabelTask(EbizUser currentUser, EbizCompany currentCompany, Integer uid) {

        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(uid);
        criteria.andLabelerEqualTo(currentUser.getUserName());
        criteria.andLabelStatusEqualTo(EbizPackageLabelStatusEnum.MakingLable.getName());

        List<PackageList> list = packageListMapper.selectByExample(example);

        PackageList packageList = list.get(0);
        packageList.setLabelStatus(EbizPackageLabelStatusEnum.UnMadeLabel.getName());
        packageList.setLabeler("");
        packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        int flag = packageListMapper.updateByExample(packageList, example);
        System.out.println("-=------------------------packagesUID:" + uid);
        if (flag > 0) {
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "LabelStatus", uid, EbizPackageLabelStatusEnum.UnMadeLabel.getName());
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Labeler", uid, "");
            return true;
        }
        return false;
    }

    //查看完成的Label
    public Map<String, Object> readAllLabeledkagesForUser(EbizUser currentUser, String companyName, String currentPage, String pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andLabelStatusEqualTo("MadeLabel");
        criteria1.andLabelerEqualTo(currentUser.getUserName());
        criteria1.andCompanyNameEqualTo(companyName);

        //执行得到结果
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage)); //开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;

    }

    //更新已经领取的任务的状态
    public boolean finishCheckTask(EbizUser currentUser, EbizCompany currentCompany, Integer uid, String status) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(uid);
        criteria.andCheckerEqualTo(currentUser.getUserName());
        criteria.andCheckStatusEqualTo(EbizPackageCheckStatusEnum.Checking.getName());
        criteria.andStatusNotEqualTo(status);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        for (PackageList packageList : list) {
            packageList.setCheckStatus(EbizPackageCheckStatusEnum.Checked.getName());
            packageList.setStatus(status);
            packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
            int count = packageListMapper.updateByExample(packageList, example);
            if (count > 0) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        if (flag) {
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "CheckStatus", uid, EbizPackageCheckStatusEnum.UnChecked.getName());
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Status", uid, status);
            return true;
        }
        return false;
    }

    //取消已经领取的任务
    public boolean cancelCheckTask(EbizUser currentUser, EbizCompany currentCompany, Integer uid) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(uid);
        criteria.andCheckerEqualTo(currentUser.getUserName());
        criteria.andCheckStatusEqualTo(EbizPackageCheckStatusEnum.Checking.getName());
        example.or(criteria);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        for (PackageList packageList : list) {
            packageList.setCheckStatus(EbizPackageCheckStatusEnum.UnChecked.getName());
            packageList.setChecker("");
            packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
            int count = packageListMapper.updateByExample(packageList, example);
            if (count > 0) {
                //更新成功，继续更新
                flag = true;
            } else {
                //更新失败,停止更新
                flag = false;
                break;
            }
        }

        if (flag) {
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "CheckStatus", uid, EbizPackageCheckStatusEnum.UnChecked.getName());
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Status", uid, "");
            return true;
        }
        return false;
    }

    //为进入当前需要支付的页面准备数据
    public Map<String, Object> readPayingTasksForCompany(EbizUser currentUser, String companyName, String currentPage, String pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andPayStatusEqualTo(EbizPackagePayStatusEnum.Paying.getName());
        criteria1.andPayerEqualTo(currentUser.getUserName());

        //执行得到结果
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));//开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //去往已经完成的支付页面任务列表
    public Map<String, Object> readAllPaidkagesForUser(EbizUser currentUser, String companyName, String currentPage, String pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andPayStatusEqualTo("Paid");
        criteria.andPayerEqualTo(currentUser.getUserName());
        criteria.andCompanyNameEqualTo(companyName);
        //执行得到结果
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));//开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //取消支付任务的操作
    public boolean cancelPayTask(EbizUser currentUser, EbizCompany currentCompany, Integer uid) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(uid);
        criteria.andPayerEqualTo(currentUser.getUserName());
        criteria.andPayStatusEqualTo(EbizPackagePayStatusEnum.Paying.getName());
        example.or(criteria);
        PackageList packageList = new PackageList();
        packageList.setPayStatus(EbizPackagePayStatusEnum.UnPaid.getName());
        packageList.setPayer("");
        packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        int flag = packageListMapper.updateByExample(packageList, example);

        if (flag > 0) {
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "PayStatus", uid, EbizPackagePayStatusEnum.UnPaid.getName());
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Payer", uid, "");
            return true;
        }
        return false;
    }

    //进入到Label页面需要查找的数据
    public Map<String, Object> readAllNeedLabeListPackagesForCompany(String companyName, String currentPage, String pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andLabelStatusEqualTo("UnMade");
        criteria1.andCompanyNameEqualTo(companyName);
        criteria1.andStatusEqualTo("InStock");
        example.or(criteria1);

        PackageListExample.Criteria criteria2 = example.createCriteria();
        criteria2.andLabelStatusEqualTo("");
        criteria2.andCompanyNameEqualTo(companyName);
        criteria2.andStatusEqualTo("InStock");
        example.or(criteria2);

        PackageListExample.Criteria criteria3 = example.createCriteria();
        criteria3.andLabelStatusIsNull();
        criteria3.andCompanyNameEqualTo(companyName);
        criteria3.andStatusEqualTo("InStock");
        example.or(criteria3);

        //执行得到结果
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage)); //开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //进入到用户支付页面需要显示的数据
    public Map<String, Object> readAllNeedPayListPackagesForCompany(String companyName, String currentPage, String pageSize) {
        List<String> listStr = new ArrayList<>();
        listStr.add("Deleted");
        listStr.add("UnReceived");

        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andPayStatusEqualTo("UnPaid");
        criteria1.andCompanyNameEqualTo(companyName);
        criteria1.andStatusNotIn(listStr);
        example.or(criteria1);

        PackageListExample.Criteria criteria2 = example.createCriteria();
        criteria2.andPayStatusEqualTo("PartlyPaid");
        criteria2.andCompanyNameEqualTo(companyName);
        criteria1.andStatusNotIn(listStr);
        example.or(criteria2);

        PackageListExample.Criteria criteria3 = example.createCriteria();
        criteria3.andPayStatusIsNull();
        criteria3.andCompanyNameEqualTo(companyName);
        criteria1.andStatusNotIn(listStr);
        example.or(criteria3);


        //执行得到结果
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));//开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //条件查询
    public Map<String, Object> findAllUnCheckedPackForCompanyByCondition(PackageListBo bo, String companyName) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        //必选条件
        //criteria.andCompanyNameEqualTo(companyName);
        //选填
        //id
        if (bo.getId() != null && !"".equals(bo.getId())) {
            criteria.andIdEqualTo(bo.getId());
        }
        //username
        if (bo.getUserName() != null && !"".equals(bo.getUserName())) {
            criteria.andUserNameLike(bo.getUserName());
        }
        //价格范围
        if (bo.getPrice() != null && !"".equals(bo.getPrice())) {
            String priceStr = bo.getPrice();
            String[] params = priceStr.split("-");
            if (params.length < 2) {
                if (!params[0].equals("all")) {
                    criteria.andPriceGreaterThanOrEqualTo(Double.parseDouble(params[0]));
                } else {

                }
            } else {
                criteria.andPriceBetween(Double.parseDouble(params[0]), Double.parseDouble(params[1]));
            }
        }
        //brand
        if (bo.getBrand() != null && !"".equals(bo.getBrand())) {
            criteria.andBrandEqualTo(bo.getBrand());
        }
        //upc
        if (bo.getUpc() != null && !"".equals(bo.getUpc())) {
            criteria.andUPCEqualTo(bo.getUpc());
        }

        //邮寄状态
        if (bo.getStatus() != null && !"".equals(bo.getStatus())) {
            Integer status = bo.getStatus();
            if (status == 0) {
                //全部
            } else if (status == 1) {
                //激活状态
                criteria.andStatusEqualTo("Actived");
            } else {
                criteria.andStatusNotEqualTo("Actived");
            }
        }

        //shippingAddress
        if (bo.getShippingAddress() != null && !"".equals(bo.getShippingAddress())) {
            if ("all".equals(bo.getShippingAddress())) {

            } else {
                criteria.andShippingAddressEqualTo(bo.getShippingAddress());
            }
        }

        example.setPageSize(bo.getPageSize());
        example.setPageIndex(bo.getCurrentPage());//开始查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        System.out.println(list.size());
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }


    //通过当前数据该公司的该人领取的任务量
    public int getCheckTaskCount(EbizUser currentUser, EbizCompany currentCompany) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andCheckStatusEqualTo(EbizPackageCheckStatusEnum.Checking.getName());
        criteria.andCheckerEqualTo(currentUser.getUserName());
        int count = packageListMapper.countByExample(example);
        return count;
    }

    //判断该任务是否已经被领取成功
    public boolean takeCheckTasks(EbizUser currentUser, EbizCompany currentCompany, Integer uid) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(uid);
        criteria.andCheckStatusIsNull();
        example.or(criteria);
        //因为checkStatus可任意为空也可以为某个值，没法使用or关键字，但是可以使用两个criateria对象，然后进行or就可以了
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andIdEqualTo(uid);
        criteria1.andCheckerEqualTo(EbizPackageCheckStatusEnum.UnChecked.getName());
        example.or(criteria1);

        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        for (PackageList packageList : list) {
            packageList.setCheckStatus(EbizPackageCheckStatusEnum.Checking.getName());
            packageList.setChecker(currentUser.getUserName());
            packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
            int count = packageListMapper.updateByExample(packageList, example);
            if (count > 0) {
                //成功更新继续更新
                flag = true;
            } else {
                //更新失败，结束更新操作，回滚操作
                flag = false;
                break;
            }
        }
        if (flag) {
            //全部更新成功
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "CheckStatus", uid, EbizPackageCheckStatusEnum.Checking.getName());
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Checker", uid, currentUser.getUserName());
            return true;
        } else {
            //有些部分更新失败，事务回滚
            return false;
        }
    }

    public List<PackageList> readNeedLabeledPackForUser(EbizUser currentUser, EbizCompany currentCompany, String pickedUserName) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andLabelStatusEqualTo("UnMade");
        criteria.andUserNameEqualTo(pickedUserName);
        criteria.andStatusEqualTo("InStock");
        criteria.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria);
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andLabelStatusIsNull();
        criteria1.andUserNameEqualTo(pickedUserName);
        criteria1.andStatusEqualTo("InStock");
        criteria1.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria1);
        List<PackageList> list = new ArrayList<>();
        list = packageListMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    public int getLabelTaskCount(EbizUser currentUser, EbizCompany currentCompany) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andLabelStatusEqualTo(EbizPackageLabelStatusEnum.MakingLable.getName());
        criteria.andLabelerEqualTo(currentUser.getUserName());
        return packageListMapper.countByExample(example);
    }

    public boolean takeLabelTasks(EbizUser currentUser, EbizCompany currentCompany, Integer id) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andLabelStatusIsNull();
        criteria.andIdEqualTo(id);
        example.or(criteria);

        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andLabelStatusEqualTo(EbizPackageLabelStatusEnum.UnMadeLabel.getName());
        criteria1.andIdEqualTo(id);
        example.or(criteria1);

        List<PackageList> packageListList = packageListMapper.selectByExample(example);
        boolean flag = false;
        for (PackageList packageList : packageListList) {
            packageList.setLabelStatus(EbizPackageLabelStatusEnum.MakingLable.getName());
            packageList.setLabeler(currentUser.getUserName());
            packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
            //执行更新操作
            int count = packageListMapper.updateByExample(packageList, example);
            if (count > 0) {
                flag = true;
                //如果更新成功，就继续更新
            } else {
                //如果没更新成功就跳出循环，执行失败，应该事务回滚
                flag = false;
                break;
            }
        }

        System.out.println("------开始测试-------");
        System.out.println("flag = " + flag + "; id = " + id);
        System.out.println("------结束测试-------");
        if (flag) {
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "LabelStatus", id, EbizPackageLabelStatusEnum.MakingLable.getName());
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Labeler", id, currentUser.getUserName());
            return true;
        }
        return false;
    }


    public boolean addUpdateOperationRecord(EbizUser user, EbizCompany company, String listName, String columnName, int uid, String value) {
        //创建保存对象
        System.out.println("------------------------------开始执行保存记录操作-=-----------------------");
        OperationRecord record = new OperationRecord();
        record.setUserName(user.getUserName());
        record.setCompanyName(user.getCompanyName());
        record.setOperationName(EbizOperationNameEnum.UpdateColumn.getName());
        record.setTableName(listName);
        record.setRowId(uid);
        record.setColumnName(columnName);
        record.setNewValue(value);
        record.setTimeString(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        if (record.getNewValue().length() >= 5000) {
            record.setNewValue(record.getNewValue().substring(1, 4800));
        }
        System.out.println("count =          ============== " + record.getId());
        //执行保存操作
        int count = operationRecordMapper.insert(record);
        System.out.println("count =          ============== " + count);
        System.out.println("------------------------------结束执行保存记录操作-=-----------------------");
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    public List<PackageList> readNeedPaidPackForUser(EbizUser currentUser, EbizCompany currentCompany, String pickedUserName) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andPayStatusEqualTo("UnPaid");//未付款
        criteria.andUserNameEqualTo(pickedUserName);
        criteria.andStatusEqualTo("Deleted");
        criteria.andStatusNotEqualTo("UnReceived");
        criteria.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria);

        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andPayStatusEqualTo("PartlyPaid");//
        criteria1.andUserNameEqualTo(pickedUserName);
        criteria1.andStatusEqualTo("Deleted");
        criteria1.andStatusNotEqualTo("UnReceived");
        criteria1.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria1);

        PackageListExample.Criteria criteria2 = example.createCriteria();
        criteria2.andPayStatusEqualTo("PartlyPaid");//
        criteria2.andUserNameEqualTo(pickedUserName);
        criteria2.andStatusEqualTo("Deleted");
        criteria2.andStatusNotEqualTo("UnReceived");
        criteria2.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria2);

        example.setOrderByClause("id");
        List<PackageList> list = new ArrayList<>();
        list = packageListMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    public int getPayTaskCount(EbizUser currentUser, EbizCompany currentCompany) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andPayStatusEqualTo(EbizPackagePayStatusEnum.Paying.getName());
        criteria.andPayerEqualTo(currentUser.getUserName());
        return packageListMapper.countByExample(example);
    }

    public boolean takePayTasks(EbizUser currentUser, EbizCompany currentCompany, Integer id) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andPayStatusEqualTo(EbizPackagePayStatusEnum.UnPaid.getName());
        example.or(criteria);

        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andIdEqualTo(id);
        criteria1.andPayStatusIsNull();
        example.or(criteria1);

        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        for (PackageList packageList : list) {
            packageList.setPayStatus(EbizPackagePayStatusEnum.Paying.getName());
            packageList.setPayer(currentUser.getUserName());
            packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
            int count = packageListMapper.updateByExample(packageList, example);
            if (count > 0) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        if (flag) {
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "PayStatus", id, EbizPackagePayStatusEnum.Paying.getName());
            addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Payer", id, currentUser.getUserName());
            return true;
        } else {
            return false;
        }
    }

    public static String pad(int val) {
        return val < 10 ? "0" + val : "" + val;
    }


    public Map<String, Object> searchAllNonDeletedProductSet(EbizCompany currentCompany, String currentPage, String pageSize) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo("Deleted");
        criteria.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));//开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<ProductList> list = productListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = productListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //为去包裹管理页面做准备
    public Map<String, Object> readAllPackagesForCompany(String companyName, String currentPage, String pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo("Deleted");
        criteria.andCompanyNameEqualTo(companyName);
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));//开始分页查询的行
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> list = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //查找所有的已经激活的产品和Deal
    public Map<String, Object> searchAllActiveAndAliveDealProductsSet(String companyName, String currentPage, String pageSize) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyNameEqualTo(companyName);
        List<String> statusList = new ArrayList<>();
        statusList.add(EbizStatusEnum.Active.getName());
        statusList.add(EbizStatusEnum.LiveDeal.getName());
        criteria.andStatusIn(statusList);
        example.setPageSize(Integer.parseInt(pageSize));
        example.setPageIndex(Integer.parseInt(currentPage));
        example.setOrderByClause("id");
        List<ProductList> list = productListMapper.selectByExample(example);
        System.out.println("size = " + list.size());
        for (ProductList productList : list) {
            System.out.println("productList = " + productList.getId());
        }
        //查询在一共有多少数据
        long totalCount = productListMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("data", list);
        return map;
    }

    //----------------------------------------------------增加-------------------start-----------------------

    //----------------------------------------------------增加-------------------end-----------------------


    //----------------------------------------------------删除-------------------start-----------------------


    //----------------------------------------------------删除-------------------end-----------------------


    //----------------------------------------------------修改-------------------start-----------------------
    public boolean updatePackageBrand(EbizUser currentUser, EbizCompany currentCompany, Integer id, String productBrand) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setBrand(productBrand);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            flag = addUpdateOperationRecord(currentUser, currentCompany, "PackageList", "Brand", id, productBrand);
        }
        return flag;
    }

    public boolean updatePackageModel(EbizUser currentUser, EbizCompany currentCompany, Integer id, String productModel) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setModelNumber(productModel);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByExample(packageList, example);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            flag = addUpdateOperationRecord(currentUser, currentCompany, "PackageList", "ModelNumber", id, productModel);
        }
        return flag;
    }

    public boolean updatePackagePayStatus(EbizUser currentUser, EbizCompany currentCompany, Integer id, String payStatus) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setPayStatus(payStatus);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "PatStatus", id, payStatus);
        }
        return flag;
    }


    public boolean updatePackageStatus(EbizUser currentUser, EbizCompany currentCompany, Integer id, String statusString) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setStatus(statusString);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Status", id, statusString);
        }
        return flag;
    }

    public boolean updatePackageName(EbizUser currentUser, EbizCompany currentCompany, Integer id, String productName) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setProductName(productName);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "ProductName", id, productName);
        }
        return flag;
    }

    public boolean updatePackageQuantity(EbizUser currentUser, EbizCompany currentCompany, Integer id, int quantity) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setQuantity(quantity);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Quantity", id, quantity + "");
        }
        return flag;
    }

    public boolean updatePackagePrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double price) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setPrice(price);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Price", id, price + "");
        }
        return flag;
    }


    public boolean updatePackageSKU(EbizUser currentUser, EbizCompany currentCompany, Integer id, String sku) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setSKU(sku);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "SKU", id, sku);
        }
        return flag;
    }


    public boolean updatePackageUPC(EbizUser currentUser, EbizCompany currentCompany, Integer id, String upc) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setUPC(upc);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "UPC", id, upc);
        }
        return flag;
    }


    public boolean updatePackageASIN(EbizUser currentUser, EbizCompany currentCompany, Integer id, String asin) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setASIN(asin);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "ASIN", id, asin);
        }
        return flag;
    }

    public boolean updatePackageCreditCard(EbizUser currentUser, EbizCompany currentCompany, Integer id, String creditcard) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setCreditCardNumber(creditcard);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "CreditCardNumber", id, creditcard);
        }
        return flag;
    }


    public boolean updatePackageAddress(EbizUser currentUser, EbizCompany currentCompany, Integer id, String address) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setShippingAddress(address);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "ShippingAddress", id, address);
        }
        return flag;
    }


    public boolean updatePackageTracking(EbizUser currentUser, EbizCompany currentCompany, Integer id, String trackingNumber) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setTrackingNumber(trackingNumber);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "TrackingNumber", id, trackingNumber);
        }
        return flag;
    }


    public boolean updatePackageShipId(EbizUser currentUser, EbizCompany currentCompany, Integer id, String shipID) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setShipID(shipID);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "shipID", id, shipID);
        }
        return flag;
    }

    public boolean updatePackageNote(EbizUser currentUser, EbizCompany currentCompany, Integer id, String note) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setNote(note);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Note", id, note);
        }
        return flag;
    }


    public boolean updatePackageQuantityAndPrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, int quantity, double price) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setPrice(price);
                packageList.setQuantity(quantity);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        //暂时还不需要添加记录
        return flag;
    }

    public boolean updatePackageTrackingAndStatus(PackageList pack) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(pack.getId());
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setTrackingNumber(pack.getTrackingNumber());
                packageList.setStatus(pack.getStatus());
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        //暂时还不需要添加记录
        return flag;
    }


    public boolean updatePackageTrackingLabelStatusAndLabeler(EbizUser currentUser, EbizCompany company, Integer id, String tracking, String shipid) {
        PackageList packageList = findPackage(id);
        packageList.setTrackingNumber(tracking);
        packageList.setShipID(shipid);
        packageList.setStatus(EbizPackageStatusEnum.EmailedLabel.getColumnName());
        packageList.setLabelStatus(EbizPackageLabelStatusEnum.MadeLabel.getName());
        packageList.setLabeler(currentUser.getUserName());
        packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        int count = packageListMapper.updateByPrimaryKey(packageList);
        if (count > 0) {
            addUpdateOperationRecord(currentUser, company, "packageList", "TrackingNumber", id, tracking);
            addUpdateOperationRecord(currentUser, company, "packageList", "ShipID", id, shipid);
            addUpdateOperationRecord(currentUser, company, "packageList", "Status", id, EbizPackageStatusEnum.EmailedLabel.getColumnName());
            addUpdateOperationRecord(currentUser, company, "packageList", "LabelStatus", id, EbizPackageLabelStatusEnum.MadeLabel.getName());
            addUpdateOperationRecord(currentUser, company, "packageList", "Labeler", id, currentUser.getUserName());
            return true;
        } else {
            return false;
        }
    }


    //----------------------------------------------------修改-------------------end-----------------------


    //----------------------------------------------------查看-------------------start-----------------------

    public PackageList findPackage(int uid) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(uid);
        List<PackageList> list = packageListMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    public List<PackageList> readPackagesByTracking(String oneTrackingString) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andTrackingNumberLike(oneTrackingString);
        example.setOrderByClause("id");
        List<PackageList> packageLists = packageListMapper.selectByExample(example);
        return packageLists;
    }


    //----------------------------------------------------查看-------------------end-----------------------

    //查找


    //更新操作


}
