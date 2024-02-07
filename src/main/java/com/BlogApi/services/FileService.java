package com.BlogApi.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String UploadImage(String path ,MultipartFile file ) throws IOException;
	InputStream GetResource(String path,String fileName) throws IOException;
}
