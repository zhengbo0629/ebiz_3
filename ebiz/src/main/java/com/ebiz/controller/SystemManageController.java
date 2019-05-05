package com.ebiz.controller;

import com.ebiz.SpringContextUtils;
import com.ebiz.common.ebizEnum.EbizOperationNameEnum;
import com.ebiz.common.ebizEnum.EbizStatusEnum;
import com.ebiz.dao.EbizUserMapper;
import com.ebiz.model.EbizUser;
import com.ebiz.model.EbizUserExample;
import com.ebiz.model.PageSplitHelper;
import com.ebiz.model.ResultData;
import com.ebiz.service.EBizUserService;
import com.ebiz.service.OperationRecordService;
import com.ebiz.utils.StringUtils;
import com.ebiz.utils.security.SHASecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ebiz.common.Constant.SESSION_KEY_USER;

@RestController
public class SystemManageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemManageController.class);
    @Autowired
    private EBizUserService userService;
    @Autowired
    private EbizUserMapper ebizUserMapper;
    @Autowired
    private OperationRecordService operationRecordService;

    /**
     * 获取所有的医生列表
     *
     * @param pageIndex 页数
     * @param pageSize 每页记录数
     * @return 医生列表
     */
    @GetMapping("/system-manage/doctor/list")
    public ResultData getDoctors(EbizUser record,
                                 @RequestParam int pageIndex,
                                 @RequestParam int pageSize) {
        EbizUserExample example=new EbizUserExample();

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

    @PostMapping("/system-manage/doctor")
    public ResultData updateDoctorInfo(@RequestParam int id,
                                       String password,
                                       String introducer,
                                       String[] permission,
                                       @RequestParam String status,
                                       Double balance) {

        EbizUser user = userService.get(id);
        if (StringUtils.isNotEmpty(password)) {
            password = SHASecurity.toSHA256(password);
            user.setPassWord(password);
        }
        if (StringUtils.isNotEmpty(introducer)) {
            user.setIntroducer(introducer);
        }
        if (permission != null) {
            user.setPermissions(EBizUserService.getUserPermissionString(permission));
        }
        if (status.equalsIgnoreCase(EbizStatusEnum.Active.getName())) {
            user.setStatus(EbizStatusEnum.Active.getName());
        } else if (status.equalsIgnoreCase(EbizStatusEnum.UnActive.getName())) {
            user.setStatus(EbizStatusEnum.UnActive.getName());
        } else {
            user.setStatus(EbizStatusEnum.UnActive.getName());
        }
        if (balance == null) {
            balance = 0D;
        }
        user.setBalance(balance);
        ebizUserMapper.updateByPrimaryKey(user);
        //操作记录
        operationRecordService.addOperationRecord(
                (EbizUser) SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER),
                "ebizuser",
                EbizOperationNameEnum.UpdateColumn);
        return ResultData.SUCCESS;
    }
}
