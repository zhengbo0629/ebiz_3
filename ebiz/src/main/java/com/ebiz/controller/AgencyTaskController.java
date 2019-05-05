/**
 * @(#)com.ebiz.controller.AgencyTaskController Copyright (c) 2014-2018 ...
 * <p>
 * DESC:
 */
package com.ebiz.controller;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.SpringContextUtils;
import com.ebiz.model.AgeTaskAndPro;
import com.ebiz.model.AgencyTask;
import com.ebiz.model.EbizUser;
import com.ebiz.model.ProductList;
import com.ebiz.service.AgencyTaskService;
import com.ebiz.service.EBizUserService;
import com.ebiz.service.EbizProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ebiz.common.Constant.SESSION_KEY_USER;

/**
 *
 * @author 王润松
 * @version 1.0  2018/11/6 0006
 */
@Controller
@RequestMapping("/AgencyTask")
public class AgencyTaskController {
    @Autowired
    private EbizProductService ebizProductService;
    private AgencyTaskService  agencyTaskService;
    private EBizUserService ebizUserService;
    /*
       发布代购任务
     */
    @RequestMapping(value="/addTask", method= RequestMethod.POST)
    public void addTask(@ModelAttribute AgeTaskAndPro ageTaskAndPro, Model model){
        AgencyTask agencyTask = new AgencyTask();
        ProductList productList = new ProductList();
        EbizUser ebizUser = (EbizUser)SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        Integer buyerId = ebizUser.getId();//当前登入的客户id
        productList.setProductName(noNullAndTrim(ageTaskAndPro.getProductName()));
        productList.setBrand(noNullAndTrim(ageTaskAndPro.getBrand()));
        productList.setModel(noNullAndTrim(ageTaskAndPro.getModel()));
        productList.setPromotQuantity(ageTaskAndPro.getPromotQuantity());
        productList.setPrice(ageTaskAndPro.getPrice());//现在价格
        productList.setPromotPrice(ageTaskAndPro.getPromotPrice());//加价价格
        ebizProductService.addTaskProduct(productList);
        agencyTask.setBuyerAddress(noNullAndTrim(ageTaskAndPro.getBuyerAddress()));
        agencyTask.setBuyerName(noNullAndTrim(ageTaskAndPro.getBuyerName()));
        agencyTask.setBuyerTel(noNullAndTrim(ageTaskAndPro.getBuyerTel()));
        agencyTask.setBuyerLeaveMag(noNullAndTrim(ageTaskAndPro.getBuyerLeaveMag()));
        agencyTask.setBuyerId(buyerId.toString());
        agencyTaskService.addTask(agencyTask);
    }

    /*
      按条件查询代购任务
     */
    @RequestMapping(value="/findByExample", method= RequestMethod.POST)
    public String findByExample(@RequestParam Integer pageNumber, Integer pageSize, AgeTaskAndPro ageTaskAndPro){
        EbizUser ebizUser = (EbizUser)SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        String userId = ebizUser.getId().toString();//当前登入的客户id
        String buyerName = ageTaskAndPro.getBuyerName();
        String taskStatus = ageTaskAndPro.getTaskStatus();
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
        List<AgeTaskAndPro> list = null;
        AgencyTask agencyTask = new AgencyTask();
        if(buyerName!=null&&buyerName!=""){//任务发起人查所有个人任务
            agencyTask.setBuyerId(userId);
            PageHelper.startPage(pageNumber, pageSize);
            list = agencyTaskService.getAgencyTaskList(agencyTask);
        }
        if(taskStatus!=null&&taskStatus!=""&&buyerName!=null&&buyerName!=""){//任务发起人查已发布的任务、已被接受、已被完成的任务
            agencyTask.setTaskStatus(taskStatus);
            agencyTask.setBuyerId(userId);
            PageHelper.startPage(pageNumber, pageSize);
            list = agencyTaskService.getAgencyTaskList(agencyTask);
        }

        if(taskStatus=="1"&&(buyerName==null||buyerName=="")){//代购人员查询所有可被领取的任务
            agencyTask.setTaskStatus(taskStatus);
            PageHelper.startPage(pageNumber, pageSize);
            list = agencyTaskService.getAgencyTaskList(agencyTask);
        }

        if((buyerName==null||buyerName=="")&&taskStatus!=null&&taskStatus!=""){//代购人员按任务状态查询
            agencyTask.setTaskStatus(taskStatus);
            agencyTask.setCreateBy(userId);
            PageHelper.startPage(pageNumber, pageSize);
            list = agencyTaskService.getAgencyTaskList(agencyTask);
        }else{
            list = agencyTaskService.getAgencyTaskList(agencyTask);
        }
        PageInfo pageInfo = new PageInfo(list);
        Map map= new HashMap();
        map.put("pageInfo", pageInfo);
        new JSONObject(map);
        String json = new JSONObject(map).toString();
        return json;
    }

    /*
     代购人员领取任务
    */
    @RequestMapping(value="/getAgeTask", method= RequestMethod.POST)
    public void getAgeTask(@RequestParam AgencyTask agencyTask){
        EbizUser ebizUser = (EbizUser)SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        String userId = ebizUser.getId().toString();//当前登入的客户id
        agencyTask.setCreateBy(userId);
        agencyTaskService.updateByKey(agencyTask);
    }

    /*
      字符串非空处理
     */
    public static String noNullAndTrim(String str){
        if(str == null){
            return "";
        }
        str = str.trim();
        if("NULL".equalsIgnoreCase(str)){
            return "";
        }
        return str;
    }
}
