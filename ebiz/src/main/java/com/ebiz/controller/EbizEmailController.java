package com.ebiz.controller;

import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizUser;
import com.ebiz.model.ResultData;
import com.ebiz.model.ResultState;
import com.ebiz.utils.FileUtil;
import com.ebiz.utils.SendEmailUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ebiz.utils.FileUtil.saveUploadedFiles;

@RestController
@RequestMapping("/email")
public class EbizEmailController {

    @RequestMapping("/sendEmail")
    public Map sendEmail(HttpServletRequest request){
        Map<String , Object > map = new HashMap<>();
        String message = "";
        EbizCompany company;
        company = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String emailAddress = request.getParameter("emailAddress");
        String emailTitle = request.getParameter("emailTitle");
        String emailContent = request.getParameter("emailContent");
        emailContent = emailContent + "\n\nRegards\n" + currentUser.getUserName() + "(" + company.getCompanyName() + ")";

        emailContent=emailContent.replace("\n", "<br>");
        List<File> uploadedFiles = null;
        try {
            uploadedFiles = FileUtil.saveUploadedFiles(request, company);
            List<String> chosenFileStrings = new ArrayList<>();
            for (int i = 0; i < uploadedFiles.size(); i++) {
                chosenFileStrings.add(uploadedFiles.get(i).getAbsolutePath());
            }
            if (SendEmailUtil.sendEmailtoOneRecipientFromCompany(company.getEmail() , company.getEmailPassword(), emailAddress, emailTitle,
                    emailContent, chosenFileStrings)) {
                message = message + "Email Send Sucessfully; \n";
                ResultData resultData = new ResultData(ResultState.SUCCESS , message);
                map.put("data" , resultData);
            } else {
                message = message + "Send Email Failed, Please Try Again; \n";
                ResultData resultData = new ResultData(ResultState.FAIL , message);
                map.put("data" , resultData);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            FileUtil.deleteUploadFiles(uploadedFiles);
        }
        return map;
    }






}
