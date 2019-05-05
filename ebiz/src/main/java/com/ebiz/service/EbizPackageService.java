package com.ebiz.service;

import com.ebiz.common.ebizEnum.*;
import com.ebiz.dao.PackageListMapper;
import com.ebiz.model.*;
import com.ebiz.utils.GeneralMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EbizPackageService {


    @Autowired
    private PackageListMapper packageListMapper ;

    @Autowired
    private OperationRecordService operationRecordService;


    /**
     * 添加一个包裹
     * @param currentUser
     * @param currentCompany
     * @param tempPackage
     * @return
     */
    public void addPackage(EbizUser currentUser, EbizCompany currentCompany, PackageList tempPackage) throws  Exception{
        int id = packageListMapper.insertAndGetMaxId(tempPackage);
        operationRecordService.addAddOperationRecord(currentUser,currentCompany,"packageList",tempPackage.getId());
    }

    /**
     * 批量查找包裹通过ids
     * @param uidList
     * @return
     */
    public List<PackageList> findPackages(List<Integer> uidList) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(uidList);
        return packageListMapper.selectByExample(example);
    }

    /**
     * 查找该用户已经领取对单包裹的数量
     * @param currentUser
     * @param currentCompany
     * @return
     */
    public int getLabelTaskCount(EbizUser currentUser, EbizCompany currentCompany) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andLabelStatusEqualTo(EbizPackageLabelStatusEnum.MakingLable.getName());
        criteria.andLabelerEqualTo(currentUser.getUserName());
        return packageListMapper.countByExample(example);
    }

    /**
     * 用户领取该任务
     * @param currentUser
     * @param currentCompany
     * @param id
     * @return
     */
    public boolean takeLabelTasks(EbizUser currentUser, EbizCompany currentCompany, Integer id) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andCheckStatusIsNull();
        criteria.andIdEqualTo(id);
        example.or(criteria);
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andCheckStatusEqualTo(EbizPackageCheckStatusEnum.UnChecked.getName());
        criteria1.andIdEqualTo(id);
        example.or(criteria1);

        List<PackageList> packageListList = packageListMapper.selectByExample(example);
        boolean flag = false;
        for (PackageList packageList : packageListList) {
            packageList.setCheckStatus(EbizPackageCheckStatusEnum.Checking.getName());
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
        if (flag) {
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "LabelStatus", id, EbizPackageLabelStatusEnum.MakingLable.getName());
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Labeler", id, currentUser.getUserName());
            return true;
        }
        return false;
    }

    /**
     * 取消一个label任务
     * @param currentUser
     * @param currentCompany
     * @param uid
     * @return
     */
    public boolean cancelLabelTask(EbizUser currentUser, EbizCompany currentCompany, Integer uid) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(uid);
        criteria.andLabelerEqualTo(currentUser.getUserName());
        criteria.andLabelStatusEqualTo(EbizPackageLabelStatusEnum.MakingLable.getName());
        List<PackageList > list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(PackageList packageList : list){
                packageList.setLabelStatus(EbizPackageLabelStatusEnum.UnMadeLabel.getName());
                packageList.setLabeler("");
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
        }
        if (flag) {
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "LabelStatus", uid, EbizPackageLabelStatusEnum.UnMadeLabel.getName());
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Labeler", uid,  "");
            return true;
        }
        return false;
    }


    /**
     *
     * @param currentUser
     * @param currentCompany
     * @param pickedUserName
     * @return
     */
    public List<PackageList> readNeedPaidPackForUser(EbizUser currentUser, EbizCompany currentCompany, String pickedUserName) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andPayStatusEqualTo("UnPaid");//未付款
        criteria.andUserNameEqualTo(pickedUserName);
        List<String > statusList = new ArrayList<>();
        statusList.add("Deleted");
        statusList.add("UnReceived");
        criteria.andStatusNotIn(statusList);
        criteria.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria);

        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andPayStatusEqualTo("PartlyPaid");//
        criteria1.andUserNameEqualTo(pickedUserName);
        criteria.andStatusNotIn(statusList);
        criteria1.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria1);

        PackageListExample.Criteria criteria2 = example.createCriteria();
        criteria2.andPayStatusIsNull();
        criteria2.andUserNameEqualTo(pickedUserName);
        criteria.andStatusNotIn(statusList);
        criteria2.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria2);

        List<PackageList> list = new ArrayList<>();
        list = packageListMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }


    /**
     * 领取该支付任务
     * @param currentUser
     * @param currentCompany
     * @param id
     * @return
     */
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "PayStatus", id, EbizPackagePayStatusEnum.Paying.getName());
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Payer", id, currentUser.getUserName());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查找该用户领取多少支付的任务
     * @param currentUser
     * @param currentCompany
     * @return
     */
    public int getPayTaskCount(EbizUser currentUser, EbizCompany currentCompany) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andPayStatusEqualTo(EbizPackagePayStatusEnum.Paying.getName());
        criteria.andPayerEqualTo(currentUser.getUserName());
        return packageListMapper.countByExample(example);
    }

    //获取制定公司的最近两个月拥有五个包裹的用户名称
    public List<String> getUserNameByCountPackage(String companyName , Integer count , Integer month){
        return packageListMapper.getUserNameByCountPackage(companyName , count , month);
    }


    /**
     * 通过包裹ID查找包裹详情失败
     * @param packageId
     * @return
     */
    public PackageList findPackageById(Integer packageId) {
        return packageListMapper.selectByPrimaryKey(packageId);
    }


    /**
     * 取消支付任务
     * @param currentUser
     * @param currentCompany
     * @param id
     * @return
     */
    public boolean cancelPayTask(EbizUser currentUser, EbizCompany currentCompany, Integer id){
        PackageList packageList = findPackageById(id);
        packageList.setPayStatus(EbizPackagePayStatusEnum.UnPaid.getName());
        packageList.setPayer(null);
        packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        int count = packageListMapper.updateByPrimaryKey(packageList);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    public void updatePackageModel(EbizUser currentUser, EbizCompany currentCompany, Integer id, String productModel) throws Exception{
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setModelNumber(productModel);
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "PackageList", "ModelNumber", id, productModel);
        }
    }


    public void updatePackageBrand(EbizUser currentUser, EbizCompany currentCompany, Integer id, String productBrand) throws  Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "PackageList", "Brand", id, productBrand);
        }
    }


    public void updatePackagePayStatus(EbizUser currentUser, EbizCompany currentCompany, Integer id, String payStatus) throws  Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "PatStatus", id, payStatus);
        }
    }

    public void updatePackageStatus(EbizUser currentUser, EbizCompany currentCompany, Integer id, String statusString) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Status", id, statusString);
        }
    }

    public void updatePackageName(EbizUser currentUser, EbizCompany currentCompany, Integer id, String productName) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "ProductName", id, productName);
        }
    }

    public void updatePackageQuantity(EbizUser currentUser, EbizCompany currentCompany, Integer id, int quantity) throws  Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Quantity", id, quantity + "");
        }
    }

    public void updatePackagePrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double price) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Price", id, price + "");
        }
    }

    public void updatePackageSKU(EbizUser currentUser, EbizCompany currentCompany, Integer id, String sku) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "SKU", id, sku);
        }
    }

    public void updatePackageUPC(EbizUser currentUser, EbizCompany currentCompany, Integer id, String upc) throws  Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "UPC", id, upc);
        }
    }

    public void updatePackageASIN(EbizUser currentUser, EbizCompany currentCompany, Integer id, String asin) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "ASIN", id, asin);
        }
    }

    public void updatePackageCreditCard(EbizUser currentUser, EbizCompany currentCompany, Integer id, String creditcard) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "CreditCardNumber", id, creditcard);
        }
    }

    public void updatePackageAddress(EbizUser currentUser, EbizCompany currentCompany, Integer id, String address) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "ShippingAddress", id, address);
        }
    }

    public void updatePackageTracking(EbizUser currentUser, EbizCompany currentCompany, Integer id, String trackingNumber) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "TrackingNumber", id, trackingNumber);
        }
    }

    public void updatePackageShipId(EbizUser currentUser, EbizCompany currentCompany, Integer id, String shipID) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "shipID", id, shipID);
        }
    }

    public void updatePackageNote(EbizUser currentUser, EbizCompany currentCompany, Integer id, String note) throws Exception{
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
            operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Note", id, note);
        }
    }


    public void updatePackageQuantityAndPrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, int quantity, double price) throws Exception{
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<PackageList> list = packageListMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setPrice(price);
                packageList.setQuantity(quantity);
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
            }
        }
    }

    public void updatePackageTrackingAndStatus(PackageList pack) throws Exception{
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(pack.getId());
        List<PackageList> list = packageListMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            for (PackageList packageList : list) {
                packageList.setTrackingNumber(pack.getTrackingNumber());
                packageList.setStatus(pack.getStatus());
                packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByPrimaryKey(packageList);
                if (count > 0) {
                } else {
                    break;
                }
            }
        }
    }

    public void updatePackageTrackingLabelStatusAndLabeler(EbizUser currentUser, EbizCompany company, Integer id, String tracking, String shipid) throws  Exception{
        PackageList packageList = packageListMapper.selectByPrimaryKey(id);
        String timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);
        packageList.setTrackingNumber(tracking);
        packageList.setShipID(shipid);
        packageList.setLabeler(currentUser.getUserName());
        packageList.setStatus(EbizPackageStatusEnum.EmailedLabel.getColumnName());
        packageList.setLabelStatus(EbizPackageLabelStatusEnum.MadeLabel.getName());
        packageList.setUpdateTime(timeString);
        operationRecordService.addUpdateOperationRecord(currentUser, company, "packageList", "TrackingNumber", id,  tracking);
        operationRecordService.addUpdateOperationRecord(currentUser, company, "packageList", "ShipID", id,  shipid);
        operationRecordService.addUpdateOperationRecord(currentUser, company, "packageList", "Status", id,  EbizPackageStatusEnum.EmailedLabel.getColumnName());
        operationRecordService.addUpdateOperationRecord(currentUser, company, "packageList", "LabelStatus", id, EbizPackageLabelStatusEnum.MadeLabel.getName());
        operationRecordService.addUpdateOperationRecord(currentUser, company, "packageList", "Labeler", id, currentUser.getUserName());
    }

    /**
     * 软删除
     * @param currentUser
     * @param currentCompany
     * @param uid
     * @throws Exception
     */
    public void deletePackage(EbizUser currentUser, EbizCompany currentCompany, int uid) throws Exception{
        PackageList packageList = packageListMapper.selectByPrimaryKey(uid);
        packageList.setStatus(EbizStatusEnum.Deleted.getName());
        packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000));
        packageListMapper.updateByPrimaryKeySelective(packageList);
    }

    /**
     * 更新包裹信息会用到该方法
     * @param oneTrackingString
     * @return
     */
    public List<PackageList> readPackagesByTracking(String oneTrackingString) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andTrackingNumberLike(oneTrackingString);
        example.setOrderByClause("id");
        List<PackageList> packageLists = packageListMapper.selectByExample(example);
        return packageLists;
    }


    /**
     * 领取发送label任务的controller用到了
     * @param currentUser
     * @param currentCompany
     * @param pickedUserName
     * @return
     */
    public List<PackageList> readNeedLabeledPackForUser(EbizUser currentUser, EbizCompany currentCompany, String pickedUserName) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andLabelStatusEqualTo("UnMade");
        criteria.andUserNameEqualTo(currentUser.getUserName());
        criteria.andStatusEqualTo("InStock");
        criteria.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria);
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andLabelStatusEqualTo("UnMade");
        criteria1.andUserNameEqualTo(currentUser.getUserName());
        criteria1.andStatusEqualTo("InStock");
        criteria1.andCompanyNameEqualTo(currentCompany.getCompanyName());
        example.or(criteria1);
        return packageListMapper.selectByExample(example);
    }
}
