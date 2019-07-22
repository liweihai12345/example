package com.example.demo.util;

import com.example.demo.model.SmsTemplateDO;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SmsUtil {


    /**
     * 随机生成6位密码
     *
     * @return
     */
    public static String getRandomCode() {
        Random random = new Random();
        String x = random.nextInt(899999) + "";
        if (x.length() < 6) {
            for (int i = x.length(); i < 6; i++) {
                x += "0";
            }
        }
        return x;
    }

    //变量匹配
    private static Pattern PARAM_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");
    private static Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    public static String convert(SmsTemplateDO temp, String code) {

        String template = temp.getContent();
        while (template.contains("${")) {
            Matcher matcher = PARAM_PATTERN.matcher(template);
            if (matcher.find()) {
                String ele = "code";
                template = template.replaceFirst("\\$\\{" + ele + "\\}", code);
            }
        }
        log.info(template);
        return template;
    }

    //替换字符
    public static String replaceStr(String template, String code) {

        while (template.contains("{")) {
            Matcher matcher = PATTERN.matcher(template);
            if (matcher.find()) {
                String ele = "code";
                template = template.replaceFirst("\\{" + ele + "\\}", code);
            }
        }
        return template;
    }


}
