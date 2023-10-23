package com.smartcontactmanager.smartcontactmanager.helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class FileSaveHelper {
    public boolean saveFile(MultipartFile mFile,String fileName){
        boolean re = false;
        try{
            String upload_dir = new ClassPathResource("/static/images/").getFile().getAbsolutePath();
            Files.copy(mFile.getInputStream(),Paths.get(upload_dir + File.separator + fileName),StandardCopyOption.REPLACE_EXISTING);
            re  = true;   
        }catch(Exception e){
            e.printStackTrace();
        }
       

        return re;
    }
}
