package com.sparta.clone.service;

import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        String book = "A13,A4,B3,F13,G15";
        convert(book);
    }
    public static void convert(String book){
        String[] temp_list=book.split(",");
        Pattern patternA = Pattern.compile("^A+[0-9]{0,2}$");
        Pattern patternB = Pattern.compile("^B+[0-9]{0,2}$");
        Pattern patternC = Pattern.compile("^C+[0-9]{0,2}$");
        Pattern patternD = Pattern.compile("^D+[0-9]{0,2}$");
        Pattern patternE = Pattern.compile("^E+[0-9]{0,2}$");
        Pattern patternF = Pattern.compile("^F+[0-9]{0,2}$");
        Pattern patternG = Pattern.compile("^G+[0-9]{0,2}$");
        Pattern patternH = Pattern.compile("^H+[0-9]{0,2}$");
        Pattern patternI = Pattern.compile("^I+[0-9]{0,2}$");

        for (int i = 0; i < temp_list.length; i++) {
            System.out.println(temp_list[i]);
            if(patternA.matcher(temp_list[i]).matches()){
                System.out.println("A");
            }
            if(patternB.matcher(temp_list[i]).matches()){
                System.out.println("B");
            }
            if(patternC.matcher(temp_list[i]).matches()){
                System.out.println("C");
            }
            if(patternD.matcher(temp_list[i]).matches()){
                System.out.println("D");
            }
            if(patternE.matcher(temp_list[i]).matches()){
                System.out.println("E");
            }
            if(patternF.matcher(temp_list[i]).matches()){
                System.out.println("F");
            }
            if(patternG.matcher(temp_list[i]).matches()){
                System.out.println("G");
            }
            if(patternH.matcher(temp_list[i]).matches()){
                System.out.println("H");
            }
            if(patternI.matcher(temp_list[i]).matches()){
                System.out.println("I");
            }

        }
    }
}
