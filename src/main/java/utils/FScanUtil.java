package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FScanUtil {
    public static ArrayList<HashMap<String,String>> parser(String s) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        String[] lines = s.split("\\n");
        for (String line : lines) {
            if (line.startsWith("[+]")) {
                String[] parts = line.split(":");
                if (parts.length == 4) {
                    HashMap<String, String> tmp = new HashMap<>();
                    tmp.put("protocol", parts[0].split(" ")[1]);
                    tmp.put("host", parts[1]);
                    tmp.put("port", parts[2]);
                    String username = parts[3].split(" ")[0];
                    String password = parts[3].split(" ")[1];
                    tmp.put("username", username);
                    tmp.put("password", password);
                    result.add(tmp);
                }
            }
        }
        return result;
    }


    public static void main(String[] args) {
        String test = "[+] mysql:192.168.0.135:3306:root 123456\n" +
                "[+] mysql:192.168.0.147:3306:root 123456\n" +
                "[+] mssql:192.168.0.53:1433:sa 123456";

        System.out.println(FScanUtil.parser(test));
    }
}
