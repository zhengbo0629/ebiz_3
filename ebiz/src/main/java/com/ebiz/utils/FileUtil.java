package com.ebiz.utils;

import com.ebiz.common.Constant;
import com.ebiz.model.EbizCompany;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 文件工具类
 */
public class FileUtil {


    /**
     * Saves files uploaded from the client and return a list of these files
     * which will be attached to the e-mail message.
     */
    public static List<File> saveUploadedFiles(HttpServletRequest request, EbizCompany company)
            throws IllegalStateException, IOException, ServletException {
        List<File> listFiles = new ArrayList<File>();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        Collection<Part> multiparts = request.getParts();
        if (multiparts.size() > 0) {
            for (Part part : request.getParts()) {
                // creates a file to be saved
                String fileName = extractFileName(part);
                if (fileName == null || fileName.equals("")) {
                    // not attachment part, continue
                    continue;
                }
                String temp = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000).replace(":", "")
                        .replace("-", "").replace(" ", "") + "_" + fileName;
                String time=Integer.toString(GeneralMethod.getYear(System.currentTimeMillis()));
                String directoryName = Constant.FilePath + "\\" + company.getCompanyName()+"\\"+time;
                File directory = new File(directoryName);
                if (!directory.exists()) {
                    directory.mkdirs();
                    // If you require it to make the entire directory path
                    // including parents,
                    // use directory.mkdirs(); here instead.
                }

                File saveFile = new File(directoryName, temp);
                System.out.println("saveFile: " + saveFile.getAbsolutePath());
                FileOutputStream outputStream = new FileOutputStream(saveFile);

                // saves uploaded file
                InputStream inputStream = part.getInputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();

                listFiles.add(saveFile);
            }
        }
        return listFiles;
    }


    /**
     * Retrieves file name of a upload part from its HTTP header
     */
    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

    /**
     * Deletes all uploaded files, should be called after the e-mail was sent.
     */
    public static void deleteUploadFiles(List<File> listFiles) {
        if (listFiles != null && listFiles.size() > 0) {
            for (File aFile : listFiles) {
                aFile.delete();
            }
        }
    }

}
