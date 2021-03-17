package com.devil.common;

import java.util.regex.Pattern;

/**
 * @Description
 * @ClassName AlphabetHelper
 * @Author Devil
 * @date 2021.03.15 21:00
 */
public class AlphabetHelper {

    static Pattern letterPattern = Pattern.compile("^[a-zA-z]$");
    static Pattern numberPattern = Pattern.compile("^[0-9]$");
    static Pattern operatorPattern = Pattern.compile("^[+-\\\\*/=<>&!%^|]$");
    static Pattern literalPattern = Pattern.compile("^[_a-zA-z0-9]$");

    public static boolean isLetter(char c){
        return letterPattern.matcher(c + "").matches();
    }

    public static boolean isNumber(char c){
        return numberPattern.matcher(c + "").matches();
    }

    public static boolean isOperator(char c){
        return operatorPattern.matcher(c + "").matches();
    }

    public static boolean isLiteral(char c){
        return literalPattern.matcher(c + "").matches();
    }
}
