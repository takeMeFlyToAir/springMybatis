package com.zzr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by zhaozhirong on 2018/11/15.
 */
public class UploadUtil {

    private final static Logger logger = LoggerFactory.getLogger(UploadUtil.class);

    public static File getUploadDir() {
        File tmpDir = new File("/export/Data/jdtest/", "jdtest");
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
            logger.debug("Creating upload tmp dir, dir path is: {}", tmpDir.getAbsolutePath());
        }
        return tmpDir;
    }

    public static String getFileName(String companyName,String projectName){
        return ChineseToFirstLetterUtil.ChineseToFirstLetter(companyName)+"---"+ChineseToFirstLetterUtil.ChineseToFirstLetter(projectName);
    }

}
