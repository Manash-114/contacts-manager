package com.smartcontactmanager.smartcontactmanager.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

@Service
public class CloudinaryFileImpl  implements CloudinaryFile{

    @Autowired
    private Cloudinary cloudinary;
    @Override
    public Map upload(MultipartFile multipartFile) {

        Map upload=null;
        try {
            upload = cloudinary.uploader().upload(multipartFile.getBytes(), Map.of());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return upload;
    }
    
    
}
