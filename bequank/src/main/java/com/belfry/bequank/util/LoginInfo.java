package com.belfry.bequank.util;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 9:02 AM 8/2/18
 * @Modifiedby:
 */

public class LoginInfo {
    String token;
    int vericode;
    /**
     * @author: Yang Yuqing
     * @description: token is the login token, and if there is no vericode, assign it to 0.
     * @param @token
     * @param @vericode
     * @return
     * @date: 9:03 AM 8/2/18
     */
    public LoginInfo(String token,int vericode){
        this.token=token;
        this.vericode=vericode;//if no, assign 0
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setVericode(int vericode) {
        this.vericode = vericode;
    }

    public String getToken() {
        return token;
    }

    public int getVericode() {
        return vericode;
    }
}
