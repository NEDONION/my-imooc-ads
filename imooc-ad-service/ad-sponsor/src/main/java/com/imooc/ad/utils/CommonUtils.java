package com.imooc.ad.utils;

import com.imooc.ad.exception.AdException;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;


public class CommonUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    // Method-获取字符串的MD5
    public static String md5(String value) {

        return DigestUtils.md5Hex(value).toUpperCase();
    }

    // 将String日期转换为 date
    public static Date parseStringDate(String dateString)
            throws AdException {

        try {
            return DateUtils.parseDate(
                    dateString, parsePatterns
            );
        } catch (Exception ex) {
            throw new AdException(ex.getMessage());
        }
    }
}
