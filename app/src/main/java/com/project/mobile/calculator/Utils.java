package com.project.mobile.calculator;

public class Utils {
     public static boolean checkContainOperator(String s) {
         return s.contains("+") || s.contains("-")
                 || s.contains("×")|| s.contains(":");
     }

}
