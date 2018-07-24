package com.aisile.manager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aisile.common.util.FastDFSClient;
import com.aisile.pojo.entity.Result;

@RestController
public class UploadController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	
	@RequestMapping("/upload")
	public Result upload( MultipartFile file){	
		try {
			//进行文件上传
			//获取文件扩展名
			String originalFilename = file.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			FastDFSClient dfsClient = new FastDFSClient("classpath:conf/client.conf");
			String url = dfsClient.uploadFile(file.getBytes(), extName);
			return new Result(true, IMAGE_SERVER_URL+url);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "文件上传失败");
		}
		
	}
}
