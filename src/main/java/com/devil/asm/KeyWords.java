package com.devil.asm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @ClassName KeyWords
 * @Author Devil
 * @date 2021.03.15 22:27
 */
public class KeyWords {

    static String[] keywords = new String[]{
            "var",
            "if",
            "else",
            "for",
            "while",
            "break",
            "func",
            "return",
    };

    static Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

    public static boolean isKeyWord(String word) {
        return keySet.contains(word);
    }
}
