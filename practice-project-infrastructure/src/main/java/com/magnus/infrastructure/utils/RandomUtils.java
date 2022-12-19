package com.magnus.infrastructure.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author gaosong
 */
public class RandomUtils {

    /**
     * 生成32位的uuid（小写字母和数字）
     *
     * @return
     */
    public static String getUuidIn32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }

    public static String getRandomNumeric(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    /**
     * 生成16位的带数字、大写、小写的随机字符串
     *
     */
    public static String getRandomIn16() {
        StringBuilder sb = new StringBuilder(16);

        //大写字母4个
        sb.append(RandomStringUtils.random(4, 65, 90, true, true));
        //小写字母4个
        sb.append(RandomStringUtils.random(4, 65, 90, true, true));
        //数字四个
        sb.append(RandomStringUtils.randomNumeric(4));
        //随机字符四个
        sb.append(RandomStringUtils.random(4));

        //打散
        List<Character> chars = sb.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        Collections.shuffle(chars);

        return chars.stream()
                .collect(StringBuilder::new,StringBuilder::append,StringBuilder::append)
                .toString();
    }


    public static void main(String[] args) {
    }

}
