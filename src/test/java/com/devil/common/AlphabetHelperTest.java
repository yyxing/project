package com.devil.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Description
 * @ClassName AlphabetHelperTest
 * @Author Devil
 * @date 2021.03.15 21:51
 */
public class AlphabetHelperTest {

    @Test
    public void testHelper(){
        assertEquals(false,AlphabetHelper.isLetter('*'));
        assertEquals(true,AlphabetHelper.isLetter('a'));
        assertEquals(true,AlphabetHelper.isLetter('A'));
        assertEquals(false,AlphabetHelper.isLetter('-'));
        assertEquals(true,AlphabetHelper.isNumber('1'));
        assertEquals(false,AlphabetHelper.isNumber('*'));
        assertEquals(false,AlphabetHelper.isLiteral('-'));
        assertEquals(false,AlphabetHelper.isLiteral('*'));
        assertEquals(true,AlphabetHelper.isLiteral('a'));
        assertEquals(true,AlphabetHelper.isLiteral('A'));
        assertEquals(true,AlphabetHelper.isLiteral('0'));
        assertEquals(true,AlphabetHelper.isLiteral('9'));
        assertEquals(true,AlphabetHelper.isOperator('*'));
        assertEquals(true,AlphabetHelper.isOperator('/'));
        assertEquals(true,AlphabetHelper.isOperator('-'));
    }
}
