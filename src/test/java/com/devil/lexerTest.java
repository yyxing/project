package com.devil;

import com.devil.asm.Token;
import com.devil.asm.TokenType;
import com.devil.common.PeekIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Description
 * @ClassName lexerTest
 * @Author Devil
 * @date 2021.03.15 22:37
 */
public class lexerTest {

    private void assertToken(Token token, String value, TokenType tokenType) {
        assertEquals(tokenType, token.getType());
        assertEquals(value, token.getValue());
    }

    @Test
    public void test() {
        var it = new PeekIterator<>("if abc".chars().mapToObj(x -> (char) x));
        var it1 = new PeekIterator<>("true abc".chars().mapToObj(x -> (char) x));
        var it2 = new PeekIterator<>("bbc abc".chars().mapToObj(x -> (char) x));
        var token1 = Token.makeVarOrKeyWord(it);
        var token2 = Token.makeVarOrKeyWord(it1);
        var token3 = Token.makeVarOrKeyWord(it2);
        assertToken(token1, "if", TokenType.KEYWORD);
        assertToken(token2, "true", TokenType.BOOLEAN);
        assertToken(token3, "bbc", TokenType.VARIABLE);
        it.next();
        token1 = Token.makeVarOrKeyWord(it);
        assertToken(token1, "abc", TokenType.VARIABLE);
    }

    @Test
    public void test2() {
        String[] tests = {"\"123\"", "'123'"};
        for (String test : tests) {
            var it = new PeekIterator<>(test.chars().mapToObj(x -> (char) x));
            var token = Token.makeString(it);
            assertToken(token, test, TokenType.STRING);
        }
    }

    @Test
    public void test_makeOperator() {
        String[] tests = {
                "+ xxx",
                "++mmm",
                "/=g",
                "==1",
                "+=1",
                "&=das",
                "||asd",
                "&ddd",
                "%7",
                "^=111",
        };
        String[] results = {"+", "++", "/=", "==", "+=", "&=", "||", "&", "%", "^="};
        for (int i = 0; i < tests.length; i++) {
            String test = tests[i];
            var it = new PeekIterator<>(test.chars().mapToObj(x -> (char) x));
            Token token = Token.makeOp(it);
            assertToken(token, results[i], TokenType.OPERATOR);
        }
    }
}
