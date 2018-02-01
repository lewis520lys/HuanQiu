package com.lewis.cp.model;

public class CurdUserBean {

    /**
     * myurl : http://120.27.208.188/wt/wtregister?userId=null
     * loginToken : null
     * sex : 0
     * hxpass : hxnull
     * headImg : http://120.27.208.188/wt/userhead/lewis2.jpg
     * nickName : 孙建伟
     * userId : 26
     * hasException : N
     * info : succeed
     */

    public String myurl;
    public Object loginToken;
    public int sex;
    public String hxpass;
    public String headImg;
    public String nickName;
    public int userId;
    public String hasException;
    public String info;

    public String getMyurl() {
        return myurl;
    }

    public void setMyurl(String myurl) {
        this.myurl = myurl;
    }

    public Object getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(Object loginToken) {
        this.loginToken = loginToken;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHxpass() {
        return hxpass;
    }

    public void setHxpass(String hxpass) {
        this.hxpass = hxpass;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHasException() {
        return hasException;
    }

    public void setHasException(String hasException) {
        this.hasException = hasException;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
