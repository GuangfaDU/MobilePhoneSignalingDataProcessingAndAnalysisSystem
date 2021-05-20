package cn.edu.szu.mail.util.user;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * 单例模式减少IO消耗
 *
 * @author GuangfaDU
 * @version 1.0
 */
public class GenerateUserAccount {

    private static volatile GenerateUserAccount instance;

    private final Properties properties;

    private static final String PREFIX = "utvs_";

    /**
     * 已生成账号统计
     */
    private volatile int count = 0;

    private GenerateUserAccount() {
        properties = new Properties();
        try {
            properties.load(GenerateUserAccount.class.getClassLoader().getResourceAsStream("accountLog.properties"));
            count = Integer.parseInt(properties.getProperty("generatedAccount"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GenerateUserAccount getInstance() {
        if (instance == null) {
            synchronized (GenerateUserAccount.class) {
                if (instance == null) {
                    instance = new GenerateUserAccount();
                }
            }
        }

        return instance;
    }

    private synchronized void addCount() {
        ++count;
        try {
            properties.setProperty("generatedAccount", String.valueOf(count));
            FileWriter writer = new FileWriter(
                    String.valueOf(
                            GenerateUserAccount.class
                                    .getClassLoader()
                                    .getResource("accountLog.properties")).substring(6));
            properties.store(writer, "已生成账户数");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String generateUserAccount() {
        String number = String.format("%05d", count);
        addCount();
        return PREFIX + number;
    }
}
