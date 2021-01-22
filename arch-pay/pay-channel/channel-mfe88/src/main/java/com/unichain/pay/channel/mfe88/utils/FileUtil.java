package com.unichain.pay.channel.mfe88.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void write(String fileName, String content) {
//		write to file
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName, true);
            bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String read(String fileName) throws IOException {
//		read from file
        String everything = "";
        try (BufferedReader br = new BufferedReader(
                new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything += sb.toString();
        }
        return everything;
    }

    public static String readForString(String fileName, String mOrderNo) throws IOException {
//		read from file
        List<String> list = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(
                new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                if (line.contains(mOrderNo)) {
                    list.add(line);
                }
                line = br.readLine();
            }
            if (list.isEmpty() || list.size() == 0)
                list.add("没有数据");
        }
        return Tools.getGsonDisableHtml().toJson(list);
    }

    public static String readToList(String fileName) throws IOException {
//		read from file
        List<String> list = new ArrayList<String>();
        String everything = "";
        try (BufferedReader br = new BufferedReader(
                new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                list.add(line);
                line = br.readLine();
            }
            if (list.isEmpty() || list.size() == 0)
                list.add("没有数据");
            everything += Tools.getGsonDisableHtml().toJson(list);
        }
        return everything;
    }

}
