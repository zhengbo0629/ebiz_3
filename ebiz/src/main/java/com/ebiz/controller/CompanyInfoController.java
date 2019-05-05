package com.ebiz.controller;

import com.ebiz.SpringContextUtils;
import com.ebiz.common.ebizEnum.EbizCompanyAddressEnum;
import com.ebiz.common.ebizEnum.EbizCompanyPayPeriodEnum;
import com.ebiz.common.ebizEnum.EbizOperationNameEnum;
import com.ebiz.common.ebizEnum.EbizStatusEnum;
import com.ebiz.dao.EbizCompanyMapper;
import com.ebiz.dao.EbizUserMapper;
import com.ebiz.model.*;
import com.ebiz.service.EBizUserService;
import com.ebiz.service.OperationRecordService;
import com.ebiz.utils.StringUtils;
import com.ebiz.utils.security.SHASecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.ebiz.common.Constant.SESSION_KEY_COMPANY;
import static com.ebiz.common.Constant.SESSION_KEY_USER;

@RequestMapping("/company")
@RestController
public class CompanyInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EbizUserController.class);
    @Autowired
    private EbizCompanyMapper ebizCompanyMapper;
    @Autowired
    private EBizUserService eBizUserService;
    @Autowired
    private EbizUserMapper ebizUserMapper;
    @Autowired
    private OperationRecordService operationRecordService;

    @GetMapping("/pay-period")
    public ResultData getCompanyPayPeriod() {
        List<String> list = new ArrayList<>();
        for (EbizCompanyPayPeriodEnum e : EbizCompanyPayPeriodEnum.values()) {
            list.add(e.getName());
        }
        return new ResultData(list);
    }

    @GetMapping("/current")
    public ResultData getCompany() {
        EbizCompany company = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        return new ResultData(company);
    }

    /**
     * 获取当前公司用户手册
     */
    @GetMapping("/user-manual")
    public ResultData getCompanyUserManual() {
        EbizCompany company = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        return new ResultData(company.getUserManual());
    }

    /**
     * 更新用户手册
     *
     * @param userManual 用户手册
     * @return 仅返回成功失败信息
     */
    @PostMapping("/user-manual")
    public ResultData updateCompanyUserManual(String userManual) {
        if (StringUtils.isNotEmpty(userManual)) {
            EbizCompany company = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
            //更新userManual字段
            EbizCompany updateCompany = new EbizCompany();
            updateCompany.setId(company.getId());
            updateCompany.setUserManual(userManual);
            ebizCompanyMapper.updateByPrimaryKeySelective(updateCompany);
            //修改当前公司信息
            company.setUserManual(userManual);
        }
        return ResultData.SUCCESS;
    }

    @PostMapping("/address")
    public ResultData updateCompanyAddress(String addressName1, String addressName2, String addressName3,
                                           String addressDetail1, String addressDetail2, String addressDetail3,
                                           String email, String emailPassword, String phoneNumber) {
        EbizCompany company = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        EbizCompany updateCompany = new EbizCompany();
        updateCompany.setId(company.getId());
        if (StringUtils.isNotEmpty(addressName1)) {
            updateCompany.setAddressName1(addressName1);
            updateCompany.setAddressDetail1(addressDetail1);
        }
        if (StringUtils.isNotEmpty(addressName2)) {
            updateCompany.setAddressName2(addressName2);
            updateCompany.setAddressDetail2(addressDetail2);
        }
        if (StringUtils.isNotEmpty(addressName3)) {
            updateCompany.setAddressName3(addressName3);
            updateCompany.setAddressDetail3(addressDetail3);
        }
        if (StringUtils.isNotEmpty(email)) {
            updateCompany.setEmail(email);
            updateCompany.setEmailPassword(emailPassword);
        }
        if (StringUtils.isNotEmpty(phoneNumber)) {
            updateCompany.setPhoneNumber(phoneNumber);
        }
        ebizCompanyMapper.updateByPrimaryKeySelective(updateCompany);
        //更新当前公司信息
        company = ebizCompanyMapper.selectByPrimaryKey(company.getId());
        SpringContextUtils.getSession().setAttribute(SESSION_KEY_COMPANY, company);
        return ResultData.SUCCESS;
    }

    @PostMapping("/payment")
    public ResultData updateCompanyPayment(@RequestParam String year,
                                           @RequestParam String month,
                                           @RequestParam String day,
                                           String payPeriod) {
        EbizCompany company = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        EbizCompany updateCompany = new EbizCompany();
        updateCompany.setId(company.getId());
        if ("0".equals(year)) {

        } else {
            updateCompany.setPayTime(year + "-" + month + "-" + day);
            if (StringUtils.isNotEmpty(payPeriod)) {
                updateCompany.setPayPeriod(payPeriod);
            }
            ebizCompanyMapper.updateByPrimaryKeySelective(updateCompany);
            //更新当前公司信息
            company.setPayTime(updateCompany.getPayTime());
            if (StringUtils.isNotEmpty(payPeriod)) {
                company.setPayPeriod(updateCompany.getPayPeriod());
            }
        }
        return ResultData.SUCCESS;
    }

    /**
     * 获取当前登录对象的公司对象
     *
     * @param request
     * @return
     */
    @RequestMapping("/getCurrentCompanyClient")
    public Map getCurrentUserClient(HttpServletRequest request) {
        EbizCompany company = (EbizCompany) request.getSession().getAttribute(SESSION_KEY_COMPANY);
        Map<String, Object> map = new HashMap<>();
        ResultData resultData = new ResultData(company, ResultState.SUCCESS, "获取成功");
        map.put("data", resultData);
        return map;
    }

    @RequestMapping("/getEbizCompanyAddressEnumList")
    public Map getEbizCompanyAddressEnumList() {
        Map<String, Object> map = new HashMap<>();
        EbizCompanyAddressEnum[] ebizCompanyAddressEnums = EbizCompanyAddressEnum.values();
        List<String> list = new ArrayList<>();
        for (EbizCompanyAddressEnum ebizCompanyAddressEnum : ebizCompanyAddressEnums) {
            list.add(ebizCompanyAddressEnum.getName());
        }
        ResultData resultData = new ResultData(list, ResultState.SUCCESS, "查找所有的公司地址");
        map.put("data", resultData);
        return map;
    }


    /**
     * 获取非医生、非管理员列表
     *
     * @param pageIndex 页数
     * @param pageSize  每页记录数
     * @return 医生列表
     */
    @GetMapping("/nurse/list")
    public ResultData getNurses(EbizUser record,
                                @RequestParam int pageIndex,
                                @RequestParam int pageSize) {
        EbizUserExample example = new EbizUserExample();

        EbizUserExample.Criteria criteria = example.createCriteria();
        if (record != null) {
            if (record.getId() != null) {
                criteria.andIdEqualTo(record.getId());
            }
            if (StringUtils.isNotEmpty(record.getUserName())) {
                criteria.andUserNameLike(record.getUserName());
            }
            if (StringUtils.isNotEmpty(record.getCompanyName())) {
                criteria.andCompanyNameLike(record.getCompanyName());
            }
            if (StringUtils.isNotEmpty(record.getStatus())) {
                criteria.andStatusEqualTo(record.getStatus());
            }
        }
        criteria.andUserTypeNotIn(Arrays.asList("Doctor", "Administrator"));
        EbizCompany company = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        criteria.andCompanyNameEqualTo(company.getCompanyName());
        criteria.andStatusNotEqualTo("'Deleted'");
        //初始化分页对象 用于前台展示
        int count = ebizUserMapper.countByExample(example);
        PageSplitHelper helper = new PageSplitHelper(pageIndex, pageSize);
        helper.setTotalCount(count);
        //查询数据
        example.setPageSize(pageSize);
        example.setPageIndex(pageIndex);
        List<EbizUser> list = ebizUserMapper.selectByExample(example);
        //清除密码
        for (EbizUser ebizUser : list) {
            ebizUser.setPassWord("");
        }
        //返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        map.put("page", helper);
        return new ResultData(map);
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息、该公司用户类型、该公司下用户菜单、该用户的权限
     */
    @GetMapping("/user-info/{id}")
    public ResultData getCompanyUserInfos(@PathVariable int id) {
        EbizCompany company = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        EbizUser ebizUser = eBizUserService.get(id);
        if (!ebizUser.getCompanyName().equals(company.getCompanyName())) {
            return new ResultData(ResultState.BIZ_FAIL, "非本公司用户，无法修改");
        }
        ebizUser.setPassWord("");
        Map<String, Object> map = new HashMap<>();
        // 用户信息
        map.put("user", ebizUser);
        //公司用户类型
        map.put("userTypes", eBizUserService.getCompanyUserTypes(company));
        //该公司下该用户菜单
        map.put("userMenus", eBizUserService.getCompanyUserMenus(company));
        //用户的权限
        map.put("userPermissions", EBizUserService.getUserPermissionSet(ebizUser.getPermissions()));
        return new ResultData(map);
    }

    /**
     * 更新用户信息
     *
     * @param id            用户ID
     * @param introducer    介绍人
     * @param password      密码
     * @param newUserType   用户类型
     * @param status        用户状态
     * @param balance       余额
     * @param personalLimit 报单限制
     * @param permission    权限
     * @param userNote      userNote
     * @return 仅返回成功失败信息
     */
    @PutMapping("/user")
    public ResultData updateNurseInfo(@RequestParam int id, String introducer, String password, String newUserType,
                                      String status, Double balance, Integer personalLimit, String permission,
                                      String userNote) {
        EbizCompany company = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        EbizUser currentUser = (EbizUser) SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        EbizUser ebizUser = eBizUserService.get(id);
        if (!ebizUser.getCompanyName().equals(company.getCompanyName())) {
            return new ResultData(ResultState.BIZ_FAIL, "非本公司用户，无法修改");
        }
        StringBuilder columnNames = new StringBuilder();
        StringBuilder oldValues = new StringBuilder();
        StringBuilder newValues = new StringBuilder();
        if (StringUtils.isNotEmpty(introducer)) {
            oldValues.append(ebizUser.getIntroducer()).append(",");
            ebizUser.setIntroducer(introducer);
            newValues.append(introducer).append(",");
            columnNames.append("Introducer").append(",");
        }
        if (StringUtils.isNotEmpty(userNote)) {
            oldValues.append(ebizUser.getNote()).append(",");
            ebizUser.setNote(userNote);
            newValues.append(userNote).append(",");
            columnNames.append("Note").append(",");
        }
        if (StringUtils.isNotEmpty(password)) {
            oldValues.append("***").append(",");
            ebizUser.setPassWord(SHASecurity.toSHA256(password));
            columnNames.append("PassWord").append(",");
            newValues.append("***").append(",");
        }
        if (StringUtils.isNotEmpty(newUserType) && eBizUserService.getCompanyUserTypes(company).contains(newUserType)) {
            oldValues.append(ebizUser.getUserType()).append(",");
            ebizUser.setUserType(newUserType);
            newValues.append(newUserType).append(",");
            columnNames.append("UserType").append(",");
        }
        if (StringUtils.isNotEmpty(status) && ("Active".equals(status) || "UnActive".equals(status))) {
            oldValues.append(ebizUser.getStatus()).append(",");
            ebizUser.setStatus(status);
            newValues.append(status).append(",");
            columnNames.append("Status").append(",");
        }
        if (balance != null) {
            oldValues.append(ebizUser.getBalance()).append(",");
            ebizUser.setBalance(balance);
            newValues.append(balance).append(",");
            columnNames.append("Balance").append(",");
        }
        if (personalLimit != null) {
            oldValues.append(ebizUser.getPersonalLimit()).append(",");
            ebizUser.setPersonalLimit(personalLimit);
            newValues.append(personalLimit).append(",");
            columnNames.append("PersonalLimit").append(",");
        }
        if (StringUtils.isNotEmpty(permission)) {
            oldValues.append(ebizUser.getPermissions()).append(",");
            ebizUser.setPermissions(permission);
            newValues.append(permission).append(",");
            columnNames.append("Permissions").append(",");
        }
        //更新
        ebizUserMapper.updateByPrimaryKeySelective(ebizUser);
        LOGGER.info("更新用户信息成功");
        //记录操作记录
        operationRecordService.addOperationRecord(currentUser, "ebizuser", EbizOperationNameEnum.UpdateColumn,
                columnNames.toString(), ebizUser.getId(), newValues.toString(), oldValues.toString());
        LOGGER.info("插入操作记录成功");
        return new ResultData(ResultData.SUCCESS);
    }

    /**
     * 删除一个用户
     *
     * @param id 用户ID
     * @return 仅返回成功失败信息
     */
    @DeleteMapping("/user/{id}")
    public ResultData deleteNurse(@PathVariable int id) {
        EbizCompany company = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        EbizUser currentUser = (EbizUser) SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        EbizUser ebizUser = eBizUserService.get(id);
        if (!ebizUser.getCompanyName().equals(company.getCompanyName())) {
            return new ResultData(ResultState.BIZ_FAIL, "非本公司用户，无法修改");
        }
        EbizUser tempUser = new EbizUser();
        tempUser.setId(ebizUser.getId());
        tempUser.setStatus(EbizStatusEnum.Deleted.getName());
        ebizUserMapper.updateByPrimaryKeySelective(tempUser);
        LOGGER.info("更新用户信息成功");
        //记录操作记录
        operationRecordService.addOperationRecord(currentUser, "ebizuser", EbizOperationNameEnum.UpdateColumn,
                "Status", ebizUser.getId(), ebizUser.getStatus(), EbizStatusEnum.Deleted.getName());
        LOGGER.info("插入操作记录成功");
        return ResultData.SUCCESS;
    }
}
