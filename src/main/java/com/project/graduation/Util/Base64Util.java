package com.project.graduation.Util;

import com.project.graduation.entity.*;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64Util {
    public static String imageToBase64Str(String imagePath, Work work, int  artistId) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            String imgFile = imagePath + artistId + File.separator + work.getKey();
            File imgDirectory = new File(imgFile);
            File[] files = imgDirectory.listFiles();
            if (files != null && files.length > 0) {
                inputStream = new FileInputStream(files[0]);
                data = new byte[inputStream.available()];
                inputStream.read(data);
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
