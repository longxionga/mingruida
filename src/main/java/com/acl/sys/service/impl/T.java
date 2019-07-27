package com.acl.sys.service.impl;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author 胡锋
 * @create 2019-03-25 16:41
 */
public class T {
    public static void main(String[] args) {
        Map x = new HashMap();

        String[] a =StringUtils.split("0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151",",");
        System.out.println(a.length);
        for (int i = 0;i<a.length;i++){
            x.put(a[i]+"",a[i]+"");
            //System.out.println(a[i]+"  :"+ x.containsKey(a[i]));
        }
        for (int j = 0;j<152;j++){
            if(!x.containsKey(j+""))
            System.out.println(j+"  :"+ x.containsKey(j+""));
        }
    }
}
