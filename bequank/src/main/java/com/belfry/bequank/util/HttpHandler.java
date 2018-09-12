package com.belfry.bequank.util;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class HttpHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String getAStock(String path) throws IOException {
        URL url=new URL (path);
        HttpURLConnection conn=null;
        StringBuilder builder=new StringBuilder();
        try {
            conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode()==Message.MSG_SUCCESS){
                try(BufferedReader reader = new BufferedReader((new InputStreamReader(conn.getInputStream(),"UTF-8")))){
                    String tmp;
                    while((tmp=reader.readLine())!=null){
                        builder.append(tmp);
                        builder.append(System.lineSeparator());
                    }
                }
            }else{
                logger.info("request failed : {}", path);
            }
        }finally {
            if (conn!=null)
                conn.disconnect();
            return builder.toString();
        }
    }
}
