package com.springboot.product.utile;

import cn.hutool.core.codec.Base64;
import org.apache.commons.io.FileUtils;

import java.io.*;

import static com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode;

/**
 * 文件上传工具类
 */
public class UploadFileUtil {

    /**
     * BASE64解码成File文件
     *
     * @param path
     * @return String
     * @author
     * @description 将文件转base64字符 File转成编码成BASE64
     */
    public static File base64ToFile(String str, String path, String fileName) {
        File savefile = null;
        log("服务器生成文件开始");
        log("服务器生成文件路径：" + path);
        savefile = new File(path, fileName);
//        // 创建文件夹
//        if (savefile.exists()) {
//            savefile.delete();
//            savefile.mkdirs();
//        } else {
//            savefile.mkdirs();
//        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.decode(str);
            fos = FileUtils.openOutputStream(savefile);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            log("服务器生成文件结束");
        } catch (Exception e) {
            log("BASE64解码成File文件出现异常" + e);
            return null;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return savefile;
    }
    /**
     * log打印
     *
     * @param var
     */
    private static void log(String var) {
        System.out.println(var);
    }

    public static void main(String[] args) {
        String fileBase = fileToBase64("C:\\Users\\Administrator\\Desktop\\1.jpg");
        base64ToFile(fileBase,"D:\\1a\\测试下载","aaaaa.jpg");
    }

    /**
     * 文件转base64Str
     * @param path
     * @return
     */
    public static String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = encode(bytes);
        } catch (Exception e) {
            System.out.println("BASE64编码出现异常");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }
}
