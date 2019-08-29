package com.yunhe.demo;

import org.junit.Test;

import com.taotao.utils.FastDFSClient;





public class Demo {
	@Test
	public void imgDemo(){
		try {
			FastDFSClient fastDFSClient = new FastDFSClient("D:\\taotao\\taotao-manager-web\\src\\main\\resources\\resource\\client.conf");
			String url = fastDFSClient.uploadFile("D:\\3.jpg");
			
			System.out.println(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
