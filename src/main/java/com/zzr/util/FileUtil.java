package com.zzr.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by zhaozhirong on 2018/11/28.
 */
public class FileUtil {


    /**
     * 压缩单个文件
     * @param targetZipRealPath
     * @param targetFilePath
     * @throws Exception
     */
    public static void reZipFile(String targetZipRealPath, String targetFilePath) throws Exception {
        List<String> filePathList = new ArrayList<String>();
        filePathList.add(targetFilePath);
        reZipFiles(targetZipRealPath,filePathList);
    }

    /**
     * 压缩多个文件
     * @param targetZipRealPath
     * @param targetFilePathList
     * @throws Exception
     */
    public static void reZipFiles(String targetZipRealPath, List<String> targetFilePathList) throws Exception {
        File targetZipFile = new File(targetZipRealPath);
        InputStream in = null;
        FileOutputStream fos = null;
        ZipOutputStream zipOutputStream = null;
        try {
            fos = new FileOutputStream(targetZipFile);
            zipOutputStream = new ZipOutputStream(fos);
            for (String csvFilePath: targetFilePathList) {
                in = new FileInputStream(csvFilePath);
                String csvFileName = csvFilePath.substring(csvFilePath.lastIndexOf(File.separator) + 1);
                zipOutputStream.putNextEntry(new ZipEntry(csvFileName));
                IOUtils.copy(in, zipOutputStream);
                zipOutputStream.closeEntry();
                in.close();
            }
        } finally {
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }
}
