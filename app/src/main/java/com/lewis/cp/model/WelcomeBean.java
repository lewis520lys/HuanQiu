package com.lewis.cp.model;

/**
 * Created by Administrator on 2018/1/25.
 */

public class WelcomeBean {

    /**
     * loginToken : 98d87652cec286c992f97ce35f344e3e
     * versionUrl : http://www.pugongying.com/wt
     * hasException : N
     * info : succeed
     * version : 1.0
     */

    public String loginToken;
    public String versionUrl;
    public String hasException;
    public String info;
    public String version;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
