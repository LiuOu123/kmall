package com.kgc.kmall.kmallmanagerweb;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class KmallManagerWebApplicationTests {

    @Test
    void contextLoads() throws IOException, MyException {
        String file = this.getClass().getResource("/tracker.conf").getFile();
        ClientGlobal.init(file);
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getTrackerServer();
        StorageClient storageClient=new StorageClient(trackerServer,null);
        String orginalFilename="C:\\Users\\ASUS\\Pictures\\Saved Pictures\\d5dcadeb581eec8a12cfa3333a7b3a4.jpg";
        String[] upload_file = storageClient.upload_file(orginalFilename, "jpg", null);
        String path="http://192.168.88.130";
        for (int i = 0; i < upload_file.length; i++) {
            String s = upload_file[i];
            System.out.println("s = " + s);
            path+="/"+s;
        }
        System.out.println(path);
    }


}
