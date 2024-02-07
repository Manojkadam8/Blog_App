package com.BlogApi.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.BlogApi.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String UploadImage(String path, MultipartFile file) throws IOException {
		//file name
		String name=file.getOriginalFilename();
		
		//System.out.println(file);
		//random name generator file
		String random=UUID.randomUUID().toString();
		
		String filename1=random.concat(name.substring(name.lastIndexOf(".")));
		
		//System.out.println(filename1);
		//full path
		
		String filepath=path+ File.separator +  filename1;
		//System.out.println(filepath);
		// create folder if not created
		
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(filepath));
		
		
		return filename1;
	}

	@Override
	public InputStream GetResource(String path, String fileName) throws IOException {
		String fullpath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullpath);
		return is;
	}

}
