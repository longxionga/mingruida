package com.acl.test;

import java.util.HashMap;
import java.util.Map;

public class test2 {
    public static void main(String[] args) {
        String  s = "00003106681906043992518";
        long a = Long.valueOf(s);
        System.out.println(a);
        Map<String ,Object> m = new HashMap<>();
        m.put("s",12);
        int sm = Integer.valueOf(m.get("s").toString());
        m.put("s",sm+3);
        int smw = Integer.valueOf(m.get("s").toString());
        int qwe = Integer.valueOf(m.get("s").toString());
        int aaa = Integer.valueOf(m.get("s").toString());
    }
}
