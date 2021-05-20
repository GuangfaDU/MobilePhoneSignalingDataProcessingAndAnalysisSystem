package cn.edu.szu.mail.util.user;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 随机生成强安全系数的密码，不保证唯一性
 *
 * @author GuangfaDU
 * @version 1.0
 */
public class SecureRandomPassword {

    /**
     * 字符种类
     */
    private static final int charSize = 3;

    private static final String LOWER_STR = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_STR = "1234567890";

    /**
     * 安全随机数实例
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String secureRandomPassword(int length) {
        StringBuilder stringBuilder = new StringBuilder();

        List<Character> temp = new ArrayList<>(length);
        temp.add(randomLowerChar());
        temp.add(randomUpperChar());
        temp.add(randomNumber());

        for (int i = 3; i < length; ++i) {
            temp.add(getRandomChar(RANDOM.nextInt(charSize)));
        }
        Collections.shuffle(temp);

        for (int i = 0; i < length; ++i) {
            stringBuilder.append(temp.get(i));
        }

        return stringBuilder.toString();
    }

    private static char getRandomChar(int funcNum) {
        switch (funcNum) {
            case 0:
                return randomLowerChar();
            case 1:
                return randomUpperChar();
            default:
                return randomNumber();
        }
    }

    private static char getRandomChar(String str) {
        return str.charAt(RANDOM.nextInt(str.length()));
    }

    private static char randomLowerChar() {
        return getRandomChar(LOWER_STR);
    }

    private static char randomUpperChar() {
        return getRandomChar(UPPER_STR);
    }

    private static char randomNumber() {
        return getRandomChar(NUMBER_STR);
    }
}
