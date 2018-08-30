package com.belfry.bequank.util;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.ecs.model.v20140526.DeleteImageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class OSSHandler {

    @Value("${belfry.oss_endpoint}")
    private String endpoint;
    @Value("${belfry.oss_id}")
    private String id;
    @Value("${belfry.oss_key}")
    private String key;
    @Value("${belfry.oss_prefix}")
    private String prefix;
    @Value("${belfry.oss_dir}")
    private String dir;

    public String upload(File file) {

        if (!file.exists()) {
            return null;
        }
        OSSClient client = new OSSClient(endpoint, id, key);
        try {
            InputStream inputStream = new FileInputStream(file);
            client.putObject("bequank", dir + file.getName(), inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        client.shutdown();

        return prefix+file.getName();
    }
}
