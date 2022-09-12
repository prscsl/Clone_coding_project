package com.sparta.clone.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UtilService {

    public static List<int[]> StringToMatrix(String string){
        //"(1,2)/(14,13)"->Matrix
        List<int[]> result = new ArrayList<>();
        int[] tempInt = new int[2];
        String[] temp_string = string.split("/");
        // ["(1,2)","(14,13)"]
        for (int i = 0; i < temp_string.length; i++) {
            String[] temp_temp_string=temp_string[i].split(",");
            // ["(1","2)"]
            int temp_atoz=Integer.parseInt(temp_temp_string[0].substring(1));
            int temp_1to9=Integer.parseInt(temp_temp_string[1].substring(0,temp_temp_string[1].length()-1));
            tempInt[0]=temp_atoz;
            tempInt[1]=temp_1to9;
            result.add(tempInt);
        }
        return result;
    }

    public static void main(String[] args) {
        String string = "(1,2)/(14,13)";
        System.out.println(Arrays.asList(StringToMatrix(string)));
        System.out.println(StringToMatrix(string).get(1)[0]);
    }

}
