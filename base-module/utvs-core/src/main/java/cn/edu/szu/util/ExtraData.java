package cn.edu.szu.util;

import java.io.*;
import java.util.*;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public class ExtraData {

    public static final String BASE_DATA_FILE_PATH = "D:/GuangfaDU/data/loc-rel";
    public static final String USER_FILE_PATH = "D:/GuangfaDU/data/phone.csv";
    public static final String DEFAULT_BASE_SAVE_PATH = "D:/GuangfaDU/data/extra";
    public static final int PER_USER_IN_SINGLE_FILE = 2400;

    private static final HashMap<String, List<String>> DATA = new HashMap<>();

    private static String curUser = null;

    private static void initData(int userNums) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(USER_FILE_PATH)));
        reader.readLine();
        String line;
        int userCount = 0;
        while ((line = reader.readLine()) != null) {
            String[] info = line.split("\\|");
            DATA.putIfAbsent(info[0], new LinkedList<>());
            ++userCount;
            if (userCount == userNums) {
                break;
            }
        }

        reader.close();
    }

    private static List<String> generateDayPaths(boolean isRead, int days) {
        List<String> paths = new LinkedList<>();
        for (int i = 1; i <= days; ++i) {
            String val = String.format("%02d", i);
            String path;
            if (isRead) {
                path = BASE_DATA_FILE_PATH + "/202008" + val + "/202008" + val + ".txt";
            } else {
                path = DEFAULT_BASE_SAVE_PATH + val + ".csv";
            }
            paths.add(path);
        }

        return paths;
    }

    private static List<BufferedReader> initReaders(int days) {
        List<BufferedReader> readers = new LinkedList<>();
        List<String> paths = generateDayPaths(true, days);
        paths.forEach(path -> {
            try {
                readers.add(new BufferedReader(new InputStreamReader(new FileInputStream(path))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        return readers;
    }

    private static List<BufferedWriter> initWriters(int totalFileNums) {
        List<BufferedWriter> writers = new LinkedList<>();
        List<String> paths = generateDayPaths(false, totalFileNums);
        paths.forEach(path -> {
            try {
                writers.add(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        return writers;
    }

    private static void readFile(int days) {
        List<BufferedReader> readers = initReaders(days);
        readers.forEach(reader -> {
            String line = null;
            while (true) {
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (line == null) {
                    break;
                }

                String[] info = line.split(",");
                if (DATA.containsKey(info[0])) {
                    DATA.get(info[0]).add(line);
                }
            }

            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void saveToFile() throws IOException {
        Collection<List<String>> values = DATA.values();

        int totalFileNums;
        int size = DATA.size();
        System.out.println(size);
//        if (size % 2 == 0) {
//            totalFileNums = DATA.size() / PER_USER_IN_SINGLE_FILE;
//        } else {
//            totalFileNums = DATA.size() / PER_USER_IN_SINGLE_FILE + 1;
//        }
        totalFileNums = 5;
        List<BufferedWriter> writers = initWriters(totalFileNums);

        int count = -1, index = 0;
        for (List<String> list : values) {
            for (String line : list) {
                String user = line.split(",")[0];
                if (curUser == null) {
                    curUser = user;
                    ++count;
                } else if (!user.equals(curUser)) {
                    curUser = user;
                    ++count;
                    if (count == PER_USER_IN_SINGLE_FILE) {
                        index = (index + 1) == writers.size() ? index : (index + 1);
                        count = 0;
                    }
                }

                writers.get(index).append(line);
                writers.get(index).newLine();
            }
        }

        writers.forEach(writer -> {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void extraData(int userNums, int days) throws IOException {
        initData(userNums);
        readFile(days);
        saveToFile();
    }

    public static void test() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(DEFAULT_BASE_SAVE_PATH + ".csv")));
        HashSet<String> set = new HashSet<>();
        String line;
        while ((line = reader.readLine()) != null) {
            set.add(line.split(",")[0]);
        }
        reader.close();
        System.out.println(set.size());
    }

    public static void main(String[] args) throws IOException {
        extraData(12000, 30);
        //test();
    }
}
