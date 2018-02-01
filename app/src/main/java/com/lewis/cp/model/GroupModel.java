package com.lewis.cp.model;

public class GroupModel {

    /**
     * balance : 当前用户余额
     * betUrl : 投注表h5链接
     * managerId : 群ID
     * serviceUserName : 管理员用户名
     * hasException : N
     * rmtpUrl : 直播流地址
     * info : 已进群
     */

    public String balance;
    public String betUrl;
    public String managerId;
    public String serviceUserName;
    public String hasException;
    public String rmtpUrl;
    public String info;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBetUrl() {
        return betUrl;
    }

    public void setBetUrl(String betUrl) {
        this.betUrl = betUrl;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getServiceUserName() {
        return serviceUserName;
    }

    public void setServiceUserName(String serviceUserName) {
        this.serviceUserName = serviceUserName;
    }

    public String getHasException() {
        return hasException;
    }

    public void setHasException(String hasException) {
        this.hasException = hasException;
    }

    public String getRmtpUrl() {
        return rmtpUrl;
    }

    public void setRmtpUrl(String rmtpUrl) {
        this.rmtpUrl = rmtpUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
