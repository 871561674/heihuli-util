package com.heihuli.util;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip工具类
 *
 * @author heihuli
 */
public class ZipUtil {

    /**
     * zip压缩文件，默认生成的压缩文件目录与当前需要压缩的文件或者目录同级
     *
     * @param filePath 需要压缩的文件或者目录
     * @return 返回zip压缩文件路径
     */

    public static String zip(String filePath) throws Exception {

        String zipFilePath = null;
        File srcFile = new File(filePath);
        //获得zip文件路径
        if (srcFile.isDirectory()) {
            zipFilePath = srcFile.getParent() + srcFile.getName() + ".zip";
        } else {
            String zipFileName = "";
            if (srcFile.getName().indexOf(".") > -1)
                zipFileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf(".")) + ".zip";
            else {
                zipFileName = srcFile.getName() + ".zip";
            }
            zipFilePath = srcFile.getParent() + zipFileName;
        }
        //开始进行压缩
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilePath));
        zip(out, srcFile, srcFile.getName());
        out.close();
        return zipFilePath;
    }


    /**
     * zip压缩文件
     *
     * @param filePath
     * @param zipFilePath
     */

    public static void zip(String filePath, String zipFilePath) throws Exception {
        System.out.println("压缩中...");
        File srcFile = new File(filePath);
        //检查压缩文件路径是否存在，不存在则创建
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            zipFile.getParentFile().mkdirs();
        }
        //开始压缩
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilePath));
        zip(out, srcFile, srcFile.getName());
        out.close();
        System.out.println("压缩完成");
    }

    /**
     * 压缩指定的多个文件或者目录
     *
     * @param filePathList
     * @param zipFilePath
     * @throws Exception
     */

    public static void zip(List<String> filePathList, String zipFilePath) throws Exception {
        System.out.println("压缩中...");
        //检查压缩文件路径是否存在，不存在则创建
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            zipFile.getParentFile().mkdirs();
        }
        //开始压缩
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilePath));
        if (filePathList == null || filePathList.size() == 0) {
            throw new RuntimeException("没有指定需要压缩的文件");
        }
        for (String filePath : filePathList) {
            File srcFile = new File(filePath);
            zip(out, srcFile, srcFile.getName());
        }
        out.close();
        System.out.println("压缩完成");
    }

    private static void zip(ZipOutputStream out, File srcFile, String base) throws Exception {
        //如果需要压缩的文件是目录，则进行递归压缩处理
        if (srcFile.isDirectory()) {
            File[] fileList = srcFile.listFiles();
            //如果是空目录，也需要将该目录压缩进去，注意，此时zipentry的name必须以“/"结束
            if (fileList.length == 0) {
                // 创建zip压缩进入点base
                out.putNextEntry(new ZipEntry(base + "/"));
                out.closeEntry();
            }
            for (int i = 0; i < fileList.length; i++) {
                // 递归遍历子文件夹
                zip(out, fileList[i], base + "/" + fileList[i].getName());
            }
        } else {
            // 创建zip压缩进入点base
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(srcFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            // 输入流关闭
            in.close();
        }
    }

}
