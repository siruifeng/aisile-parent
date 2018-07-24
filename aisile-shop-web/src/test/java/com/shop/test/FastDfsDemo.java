package com.shop.test;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;


public class FastDfsDemo {
	@Test
	public void FastDfsDemo() {
		try {
			//第一步加载地址
			ClientGlobal.init("D:/eclipseWorkspase/wksp2/aisile-parent/aisile-shop-web/src/main/resources/conf/client.conf");
			//获得tracker
			TrackerClient trackerClient = new TrackerClient();
			//通过trackerClient获得trackerClient server对象
			TrackerServer trackerServer = trackerClient.getConnection();
			//创建一个storageServer的引用,可以是null
			StorageServer storageServer = null;
			//创建StorageClient,参数需要trackerServer和 storageServer
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			//使用storageClient上传文件
			String[] strings = storageClient.upload_file("E:/图片/1.jpg", ".jpg", null);
			for (String string : strings) {
				System.out.println(string);
			}
			
		} catch (IOException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
