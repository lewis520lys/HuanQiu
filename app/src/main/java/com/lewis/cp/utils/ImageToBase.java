package com.lewis.cp.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageToBase {
    //图片转化成base64字符串

           public static String getImgStr(String imgFile){
                //将图片文件转化为字节数组字符串，并对其进行Base64编码处理


                InputStream in = null;
                byte[] data = null;
               //读取图片字节数组
                try
             {
                       in = new FileInputStream(imgFile);
                     data = new byte[in.available()];
                      in.read(data);
                    in.close();
                    }
             catch (IOException e)
             {
                      e.printStackTrace();
                 }
              return new String(Base64.encodeBase64(data));
         }
}
