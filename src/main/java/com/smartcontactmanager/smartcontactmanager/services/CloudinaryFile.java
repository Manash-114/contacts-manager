package com.smartcontactmanager.smartcontactmanager.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
public interface CloudinaryFile {
    public Map upload(MultipartFile multipartFile);
}
