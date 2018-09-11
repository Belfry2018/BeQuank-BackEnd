package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.service.StockInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class StockInfoServiceImpl implements StockInfoService {

    @Override
    public JSONArray getStockInfo() {
        JSONArray ret = new JSONArray();
        //股票信息csv文件保存路径
        File file = new File(System.getProperty("user.dir") + "/temp.csv");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                JSONObject obj = new JSONObject();
                String[] segments = line.split(",");
                obj.put("code", segments[1]);//股票代码
                obj.put("name", segments[2]);//股票名称
                obj.put("price", segments[3]);//最新价格
                obj.put("RF", segments[4]);//跌涨幅
                obj.put("turnover", segments[5]);//换手率
                obj.put("PE", segments[6]);//市盈率
                obj.put("volumn", segments[7]);//当日成交量
                ret.add(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

//    public static void main(String[] args) {
//        StockInfoServiceImpl s = new StockInfoServiceImpl();
//        JSONArray jsonArray = s.getStockInfo();
//        System.out.println(jsonArray);
//        System.out.println(System.getProperty("user.dir"));
//
//    }
}

