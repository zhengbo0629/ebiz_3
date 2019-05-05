package com.ebiz.service;

import com.ebiz.common.GeneralMethod;
import com.ebiz.common.ebizEnum.EbizOperationNameEnum;
import com.ebiz.dao.OperationRecordMapper;
import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizUser;
import com.ebiz.model.OperationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationRecordService {
    @Autowired
    private OperationRecordMapper operationRecordMapper;

    /**
     * 添加操作记录
     *
     * @param user      用户
     * @param tableName 操作的表
     * @param operation 操作类型
     */
    public void addOperationRecord(EbizUser user, String tableName, EbizOperationNameEnum operation) {
        OperationRecord record = new OperationRecord();
        record.setUserName(user.getUserName());
        record.setCompanyName(user.getCompanyName());
        record.setOperationName(operation.getName());
        if (record.getNewValue() != null && record.getNewValue().length() >= 5000) {
            record.setNewValue(record.getNewValue().substring(1, 4800));
        }
        record.setTableName(tableName);
        record.setTimeString(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        operationRecordMapper.insert(record);
    }

    //////////////////////////////////////////////防止发生冲突///////////////////////////////////////

    /**
     * @param currentUser
     * @param currentCompany
     * @param tableName
     * @param id
     * @DEscription 添加记录
     * @Auther sunyinghao
     */
    public void addOperationRecord(EbizUser currentUser, EbizCompany currentCompany, String tableName, int id) {
        OperationRecord record = new OperationRecord();
        record.setUserName(currentUser.getUserName());
        record.setCompanyName(currentCompany.getCompanyName());
        record.setOperationName(EbizOperationNameEnum.AddRow.getName());
        record.setTableName(tableName);
        record.setRowId(id);
        record.setTimeString(com.ebiz.utils.GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        operationRecordMapper.insert(record);
    }


    /**
     * @param currentUser
     * @param currentCompany
     * @param tableName
     * @param id
     * @DEscription 添加记录
     * @Auther sunyinghao
     */
    public int addOperationRecord(EbizUser currentUser, EbizCompany currentCompany, String tableName, String columnName, int id, String columnValue) {
        OperationRecord record = new OperationRecord();
        record.setUserName(currentUser.getUserName());
        record.setCompanyName(currentCompany.getCompanyName());
        record.setOperationName(EbizOperationNameEnum.AddRow.getName());
        record.setTableName(tableName);
        record.setRowId(id);
        record.setTimeString(com.ebiz.utils.GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        return operationRecordMapper.insert(record);
    }

    public void addUpdateOperationRecord(EbizUser user, EbizCompany company, String tableName, String columnName, int id, String value) {
        OperationRecord record = new OperationRecord();
        record.setUserName(user.getUserName());
        record.setCompanyName(company.getCompanyName());
        record.setOperationName(EbizOperationNameEnum.UpdateColumn.getName());
        record.setTableName(tableName);
        record.setRowId(id);
        record.setColumnName(columnName);
        record.setTimeString(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        record.setNewValue(value);
        operationRecordMapper.insert(record);
    }


    public void addOperationRecord(EbizUser user, String tableName,
                                   EbizOperationNameEnum operation, String columnName,
                                   int rowId, String newValue, String oldValue) {
        OperationRecord record = new OperationRecord();
        record.setUserName(user.getUserName());
        record.setCompanyName(user.getCompanyName());
        record.setOperationName(operation.getName());
        record.setTableName(tableName);
        record.setRowId(rowId);
        record.setColumnName(columnName);
        record.setTimeString(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
        record.setNewValue(newValue);
        record.setOldValue(oldValue);
        operationRecordMapper.insert(record);
    }

    public void addOperationRecord(EbizUser user, String tableName,
                                   EbizOperationNameEnum operation,
                                   int rowId) {
        addOperationRecord(user, tableName, operation,null, rowId,null,null);
    }

    public void addAddOperationRecord(EbizUser currentUser, EbizCompany currentCompany, String tableName, int id) throws  Exception{
        OperationRecord record=new OperationRecord();
        record.setUserName(currentUser.getUserName());
        record.setCompanyName(currentCompany.getCompanyName());
        record.setOperationName(EbizOperationNameEnum.AddRow.getName());
        record.setTableName(tableName);
        record.setRowId(id);
        record.setTimeString(com.ebiz.utils.GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000));
        addOperationRecord(record);
    }

    public void addOperationRecord(OperationRecord record) throws Exception{
        operationRecordMapper.insertSelective(record);
    }
}
