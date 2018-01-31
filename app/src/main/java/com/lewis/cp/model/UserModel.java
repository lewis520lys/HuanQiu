package com.lewis.cp.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/24.
 */

public class UserModel implements Serializable{


    /**
     * hasException : N
     * user : {"userId":18,"myurl":"http://120.27.208.188/wt/wtregister?userId=18","userName":"Test123456","nickName":"小刘","headImg":"http://120.27.208.188/wt/userhead/null","sex":null,"loginToken":"437451c5b8c2e16bd913ba53ad7b26f8","hxpass":"hxe10adc3949ba59abbe56e057f20f883e"}
     * info : 登录成功
     */

    public String hasException;
    public UserBean user;
    public String info;



    public static class UserBean implements Serializable{
        /**
         * userId : 18
         * myurl : http://120.27.208.188/wt/wtregister?userId=18
         * userName : Test123456
         * nickName : 小刘
         * headImg : http://120.27.208.188/wt/userhead/null
         * sex : null
         * loginToken : 437451c5b8c2e16bd913ba53ad7b26f8
         * hxpass : hxe10adc3949ba59abbe56e057f20f883e
         */

        public int userId;
        public String myurl;
        public String userName;
        public String nickName;
        public String headImg;
        public Object sex;
        public String loginToken;
        public String hxpass;


    }
}
