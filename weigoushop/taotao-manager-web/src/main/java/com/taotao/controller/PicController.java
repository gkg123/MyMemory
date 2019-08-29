package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.utils.FastDFSClient;

@Controller
public class PicController {
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map fileUpLoad(MultipartFile uploadFile){
		String originalFilename = uploadFile.getOriginalFilename();
		String substring = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
		
		try {
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(),substring);
			String path = "http://192.168.25.133/";
			Map<String ,Object> map = new HashMap<>();
			map.put("error", 0);
			map.put("url", path+url);
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map<String ,Object> map = new HashMap<>();
			map.put("error", 1);
			map.put("message", "上传失败");
			return map;
		}
		
		
		
	}
}
