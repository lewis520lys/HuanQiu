package com.lewis.cp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */

public class HomeBean implements Serializable{

    /**
     * adverInfo : 充一百送一百，手气最佳奖励8888红包，最差奖励Iphone X!充一百送一百，手气最佳奖励8888红包，最差奖励Iphone X!充一百送一百，手气最佳奖励8888红包，最差奖励Iphone X!
     * list : [{"linkUrl":"http://www.baidu.com","imageUrl":"http://img.lanrentuku.com/img/allimg/1405/5-140526101A80-L.jpg"},{"linkUrl":"http://www.baidu.com","imageUrl":"http://img.lanrentuku.com/img/allimg/1405/5-140526101A80-L.jpg"}]
     * hasException : N
     * info : succeed
     */

    private String adverInfo;
    private String hasException;
    private String info;
    private List<ListBean> list;

    public String getAdverInfo() {
        return adverInfo;
    }

    public void setAdverInfo(String adverInfo) {
        this.adverInfo = adverInfo;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * linkUrl : http://www.baidu.com
         * imageUrl : http://img.lanrentuku.com/img/allimg/1405/5-140526101A80-L.jpg
         */

        private String linkUrl;
        private String imageUrl;

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
