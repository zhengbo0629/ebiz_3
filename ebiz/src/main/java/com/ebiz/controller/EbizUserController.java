package com.ebiz.controller;

import com.ebiz.SpringContextUtils;
import com.ebiz.common.EnumHelperUtil;
import com.ebiz.common.GeneralMethod;
import com.ebiz.common.ebizEnum.EbizOperationNameEnum;
import com.ebiz.common.ebizEnum.EbizStatusEnum;
import com.ebiz.common.ebizEnum.EbizUserPermissionEnum;
import com.ebiz.common.ebizEnum.EbizUserTypeEnum;
import com.ebiz.controller.model.Menu;
import com.ebiz.dao.EbizUserMapper;
import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizUser;
import com.ebiz.model.ResultData;
import com.ebiz.model.ResultState;
import com.ebiz.service.EBizCompanyService;
import com.ebiz.service.EBizUserService;
import com.ebiz.service.OperationRecordService;
import com.ebiz.utils.ImageValidCode.ImageCodeUtil;
import com.ebiz.utils.StringUtils;
import com.ebiz.utils.security.SHASecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import static com.ebiz.common.Constant.*;

@RestController
@RequestMapping("/user")
public class EbizUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EbizUserController.class);
    @Autowired
    private EBizUserService eBizUserService;
    @Autowired
    private EBizCompanyService eBizCompanyService;
    @Autowired
    private OperationRecordService operationRecordService;
    @Autowired
    private EbizUserMapper ebizUserMapper;

    @PostMapping("/login")
    public ResultData login(@RequestParam String userName, @RequestParam String password) {
        if (SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER) != null) {
            return new ResultData(ResultState.FAIL, "已登录");
        }
        //给密码加密
        password = SHASecurity.toSHA256(password);
        //用户名密码
        EbizUser user = eBizUserService.getEbizUserByName(userName);
        if (user == null) {
            LOGGER.warn("登录失败，用户名错误");
            return new ResultData(ResultState.FAIL, "用户名密码错误");
        }

        if (!user.getPassWord().equals(password)) {
            LOGGER.warn("登录失败，密码错误");
            return new ResultData(ResultState.FAIL, "用户名密码错误");
        }
        if (!EbizStatusEnum.Active.getName().equals(user.getStatus())) {//审核中
            return new ResultData(ResultState.FAIL, "管理员审核中,请耐心等待！");
        }
        //保存公司信息
        EbizCompany company = eBizCompanyService.getEbizCompanyByCompanyName(user.getCompanyName());
        EbizUserTypeEnum userType = EnumHelperUtil.nameOf(EbizUserTypeEnum.class, user.getUserType());
        //菜单集合
        List<Menu> menu = eBizUserService.getUserMenus(userType, user.getPermissions(), company.getPermision());
        //审核通过,登陆成功
        //清空密码
        user.setPassWord("");
        SpringContextUtils.getSession().setAttribute(SESSION_KEY_USER, user);

        SpringContextUtils.getSession().setAttribute(SESSION_KEY_COMPANY, company);
        //保存菜单
        SpringContextUtils.getSession().setAttribute(SESSION_KEY_MENUS, menu);


        String page = "/view/EbizUser/welcome.html";
        if (user.getUserType().equals(EbizUserTypeEnum.Nurse.getName())
                || user.getUserType().equals(EbizUserTypeEnum.UnTrustedNurse.getName())
                || user.getUserType().equals(EbizUserTypeEnum.TrustedNurse.getName())) {
            page = "/view/EbizPackage/payPackage.html";
        } else if (user.getUserType().equals(EbizUserTypeEnum.Doctor.getName())) {
            page = "/view/EbizEmail/SendDealToGroup.html";
        } else if (user.getUserType().equals(EbizUserTypeEnum.SelfEmployedDoctor.getName())) {
            page = "/view/EbizPackage/payPackage.html";
        } else if (user.getUserType().equals(EbizUserTypeEnum.Administrator.getName())) {
            page = "/view/CompanyInfo/userManage.html";
        }
        SpringContextUtils.getSession().setAttribute(SESSION_KEY_DEFAULT_PAGE, page);
        return ResultData.SUCCESS;
    }

    /**
     * 主账户注册 医生
     */
    private static final int ACC_TYPE_MAIN = 1;
    /**
     * 子账户注册 护士
     */
    private static final int ACC_TYPE_SUB = 2;
    /**
     * 独立账户注册 既是医生又是护士
     * independent
     */
    private static final int ACC_TYPE_INDEPENDENT = 3;

    /**
     * 执行注册操作
     */
    @PostMapping("/register")
    public ResultData register(@RequestParam Integer accType,
                               @RequestParam String userName,
                               @RequestParam String password,
                               @RequestParam String companyName,
                               @RequestParam String address,
                               @RequestParam String zipCode,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String phone,
                               @RequestParam String email,
                               @RequestParam String code) {
        if (SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER) != null) {
            return new ResultData(ResultState.ALREADY_LOGIN);
        }
        String savedCode = (String) SpringContextUtils.getSession().getAttribute(SESSION_KEY_REGISTER_CODE);
        if (StringUtils.isEmpty(savedCode) || !savedCode.equalsIgnoreCase(code)) {
            return new ResultData(ResultState.FAIL, "验证码错误或已失效！");
        }
        SpringContextUtils.getSession().removeAttribute(SESSION_KEY_REGISTER_CODE);
        password = SHASecurity.toSHA256(password);
        String createTime = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
        EbizUser user = new EbizUser();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCompanyName(companyName);
        user.setPassWord(password);
        user.setTempPassWord("11111111");
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setAddress(address + "," + zipCode);
        user.setCreateTime(createTime);
        user.setPersonalLimit(1000);
        user.setBalance(0D);
        user.setStatus(EbizStatusEnum.UnActive.getName());
        //权限范围,.无序所以用Set集合
        Set<String> permissionSet = new HashSet<>();

        EbizCompany company = new EbizCompany();
        company.setCompanyName(companyName);
        company.setOwnerName(userName);
        company.setCreateTime(createTime);
        company.setBalance(0D);

        Map<String, Object> map = new HashMap<>();
        map.put("successInfo", "");
        map.put("userNameInfo", "");
        map.put("companyNameInfo", "");
        map.put("accTypeInfo", "");
        switch (accType) {
            case ACC_TYPE_MAIN:
                EbizUser ebizUser = eBizUserService.getEbizUserByName(userName);
                EbizCompany ebizCompany = eBizCompanyService.getEbizCompanyByCompanyName(companyName);
                if (ebizUser != null) {
                    map.put("userNameInfo", "用户名已存在,请用其他用户名重新注册");
                    return new ResultData(map, ResultState.BIZ_FAIL);
                }
                if (ebizCompany != null) {
                    map.put("companyNameInfo", "公司名已存在,请用其他公司名重新注册");
                    return new ResultData(map, ResultState.BIZ_FAIL);
                }
                user.setUserType(EbizUserTypeEnum.Doctor.getName());
                    /*
                    * 遍历权限后判断
                    * */
                for (EbizUserPermissionEnum permission : EbizUserPermissionEnum.values()) {
                    if (permission.getRole().equals("DoctorDefault") || permission.getRole().equals("NurseDefault"))
                        permissionSet.add(permission.getName());
                }
                user.setPermissions(EBizUserService.getUserPermissionString(permissionSet));

                eBizUserService.add(user);
                //操作记录
                operationRecordService.addOperationRecord(user, "EbizUser", EbizOperationNameEnum.AddRow);

                for (EbizUserPermissionEnum permission : EbizUserPermissionEnum.values()) {
                    if (permission.getRole().equals("DoctorDefault") || permission.getRole().equals("NurseDefault"))
                        company.addPermissions(permission.getName());
                }
                eBizCompanyService.add(company);
                operationRecordService.addOperationRecord(user, "EbizCompany", EbizOperationNameEnum.AddRow);
                map.put("successInfo", "恭喜您，您已经注册成功，请联系网站管理员激活账号");
                return new ResultData(map, ResultState.SUCCESS);
            case ACC_TYPE_SUB:
                ebizUser = eBizUserService.getEbizUserByName(userName);
                ebizCompany = eBizCompanyService.getEbizCompanyByCompanyName(companyName);
                if (ebizUser != null) {
                    map.put("userNameInfo", "用户名已存在,请用其他用户名重新注册");
                    return new ResultData(map, ResultState.BIZ_FAIL);
                }

                if (ebizCompany == null) {
                    map.put("companyNameInfo", "公司名不存在,请输入正确的公司名称");
                    return new ResultData(map, ResultState.BIZ_FAIL);
                }
                user.setUserType(EbizUserTypeEnum.Nurse.getName());
                for (EbizUserPermissionEnum permission : EbizUserPermissionEnum.values()) {
                    if (permission.getRole().equals("NurseDefault"))
                        permissionSet.add(permission.getName());
                }
                permissionSet.add(EbizUserPermissionEnum.NursePackageManager.getName());
                user.setPermissions(EBizUserService.getUserPermissionString(permissionSet));

                eBizUserService.add(user);
                operationRecordService.addOperationRecord(user, "EbizUser", EbizOperationNameEnum.AddRow);
                map.put("successInfo", "恭喜您，您已经注册成功，请联系主账号管理员或者公司管理员激活账号");
                return new ResultData(map, ResultState.SUCCESS);
            case ACC_TYPE_INDEPENDENT:
                ebizUser = eBizUserService.getEbizUserByName(userName);
                ebizCompany = eBizCompanyService.getEbizCompanyByCompanyName(companyName);
                if (ebizUser != null) {
                    map.put("userNameInfo", "用户名已存在,请用其他用户名重新注册");
                    return new ResultData(map, ResultState.BIZ_FAIL);
                }
                if (ebizCompany != null) {
                    map.put("companyNameInfo", "公司名已存在,请用其他公司名重新注册");
                    return new ResultData(map, ResultState.BIZ_FAIL);
                }
                user.setUserType(EbizUserTypeEnum.SelfEmployedDoctor.getName());

                for (EbizUserPermissionEnum permission : EbizUserPermissionEnum.values()) {
                    if (permission.getRole().equals("NurseDefault") || permission.getRole().equals("DoctorDefault") && !permission.getGroup().equals("System Deal") && !permission.getGroup().equals("Deal Market")) {
                        permissionSet.add(permission.getName());
                    }
                }
                user.setPermissions(EBizUserService.getUserPermissionString(permissionSet));

                eBizUserService.add(user);
                operationRecordService.addOperationRecord(user, "EbizUser", EbizOperationNameEnum.AddRow);
                for (EbizUserPermissionEnum permission : EbizUserPermissionEnum.values()) {
                    if (permission.getRole().equals("NurseDefault") || permission.getRole().equals("DoctorDefault") && !permission.getGroup().equals("System Deal") && !permission.getGroup().equals("Deal Market")) {
                        company.addPermissions(permission.getName());
                    }
                }
                eBizCompanyService.add(company);
                operationRecordService.addOperationRecord(user, "EbizCompany", EbizOperationNameEnum.AddRow);
                map.put("successInfo", "恭喜您，您已经注册成功，请联系网站管理员激活账号");
                return new ResultData(map, ResultState.SUCCESS);
            default:
                map.put("accTypeInfo", "账户类型异常");
                return new ResultData(map, ResultState.BIZ_FAIL);
        }
    }

    /**
     * 执行注销操作
     */
    @GetMapping("/logout")
    public ResultData logout() {
        SpringContextUtils.getSession().removeAttribute(SESSION_KEY_USER);
        SpringContextUtils.getSession().removeAttribute(SESSION_KEY_COMPANY);
        SpringContextUtils.getSession().removeAttribute(SESSION_KEY_MENUS);
        return ResultData.SUCCESS;
    }

    @GetMapping("/{id}")
    public ResultData getUser(@PathVariable(name = "id") int id) {
        EbizUser ebizUser = eBizUserService.get(id);
        ebizUser.setPassWord("");
        Map<String, Object> map = new HashMap<>();
        map.put("user", ebizUser);
        map.put("userPermissions", EBizUserService.getUserPermissionSet(ebizUser.getPermissions()));
        map.put("allPermissions", EBizUserService.getAllMenuPermission());
        return new ResultData(map);
    }


    /**
     * 获取当前登录对象
     *
     * @param request
     * @return
     */
    @Deprecated
    @RequestMapping("/getCurrentUserClient")
    public Map getCurrentUserClient(HttpServletRequest request) {
        EbizUser user = (EbizUser) request.getSession().getAttribute(SESSION_KEY_USER);
        Map<String, Object> map = new HashMap<>();
        ResultData resultData = new ResultData(user, ResultState.SUCCESS, "获取成功");
        map.put("data", resultData);
        return map;
    }

    /**
     * 获取当前登录对象
     */
    @GetMapping("/current")
    public ResultData getCurrentUser() {
        EbizUser user = (EbizUser) SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        user.setPassWord("");
        user.setTempPassWord("");
        return new ResultData(user);
    }

    /**
     * 更新当前用户信息
     */
    @PutMapping("/current")
    public ResultData updateCurrentUser(@RequestParam String oldPassword,
                                        @RequestParam String password,
                                        @RequestParam String phone,
                                        @RequestParam String address,
                                        @RequestParam String address1,
                                        @RequestParam String address2) {
        EbizUser user = (EbizUser) SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        //当前用户的密码在会话中已经清空，去重新查询
        EbizUser tempUser = eBizUserService.get(user.getId());
        oldPassword = SHASecurity.toSHA256(oldPassword);

        if (!tempUser.getPassWord().equalsIgnoreCase(oldPassword)) {
            return new ResultData(ResultState.BIZ_FAIL, "密码错误！");
        }
        //只更新部分字段
        EbizUser updateUser = new EbizUser();
        updateUser.setId(user.getId());

        StringBuilder columnNames = new StringBuilder();
        StringBuilder oldValues = new StringBuilder();
        StringBuilder newValues = new StringBuilder();
        boolean update = false;
        if (StringUtils.isNotEmpty(phone) && !phone.trim().equals(user.getPhoneNumber())) {
            oldValues.append(user.getPhoneNumber()).append(",");
            updateUser.setPhoneNumber(phone.trim());
            newValues.append(phone.trim()).append(",");
            columnNames.append("PhoneNumber").append(",");
            update = true;
        }
        if (StringUtils.isNotEmpty(address) && !address.trim().equals(user.getAddress())) {
            oldValues.append(user.getAddress()).append(",");
            updateUser.setAddress(address.trim());
            newValues.append(address.trim()).append(",");
            columnNames.append("Address").append(",");
            update = true;
        }
        if (StringUtils.isNotEmpty(address1) && !address1.trim().equals(user.getAddress1())) {
            oldValues.append(user.getAddress1()).append(",");
            updateUser.setAddress1(address1.trim());
            newValues.append(address1.trim()).append(",");
            columnNames.append("Address1").append(",");
            update = true;
        }
        if (StringUtils.isNotEmpty(address2) && !address2.trim().equals(user.getAddress2())) {
            oldValues.append(user.getAddress2()).append(",");
            updateUser.setAddress2(address2.trim());
            newValues.append(address2.trim()).append(",");
            columnNames.append("Address2").append(",");
            update = true;
        }
        if (StringUtils.isNotEmpty(password)) {
            password = SHASecurity.toSHA256(password);
            if (!password.equalsIgnoreCase(tempUser.getPassWord())) {
                oldValues.append(user.getPassWord()).append(",");
                updateUser.setPassWord(password);
                newValues.append(password).append(",");
                columnNames.append("Password").append(",");
                update = true;
            }
        }
        if (update) {
            //更新
            ebizUserMapper.updateByPrimaryKeySelective(updateUser);
            LOGGER.info("更新用户信息成功");
            //记录操作记录
            operationRecordService.addOperationRecord(user, "ebizuser", EbizOperationNameEnum.UpdateColumn,
                    columnNames.toString(), user.getId(), newValues.toString(), oldValues.toString());
            LOGGER.info("插入操作记录成功");
        }
        return new ResultData(ResultData.SUCCESS);
    }

    @GetMapping(value = "/register-code")
    public Object getRegisterCode() {
        try {
            int method = ImageCodeUtil.genArithmeticMethod();
            int result = ImageCodeUtil.genArithmeticCode(method);
            SpringContextUtils.getSession().setAttribute(SESSION_KEY_REGISTER_CODE, result + "");
            String codeStr = ImageCodeUtil.genArithmeticCodeStr(result, method);
            BufferedImage bufferedImage = ImageCodeUtil.genNumberImage(400, 120, 10, codeStr);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64Img = encoder.encode(outputStream.toByteArray());
            return new ResultData(base64Img);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultData(ResultState.FAIL, e.getMessage());
        }
    }
}
