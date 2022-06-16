package com.spring.boot.lms.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadImage(String path, MultipartFile multipartFile) throws IOException;

    boolean deleteImage(String path, String imageName) throws IOException;

    InputStream getResource(String path, String fileName) throws FileNotFoundException;

}
