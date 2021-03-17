package com.devil.asm;

import com.devil.common.AlphabetHelper;
import com.devil.common.LexicalException;
import com.devil.common.PeekIterator;

/**
 * @Description
 * @ClassName Token
 * @Author Devil
 * @date 2021.03.14 15:22
 */
public class Token {
    private TokenType type;
    private String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    /**
     * 是否是变量标识
     *
     * @return
     */
    public boolean isVariable() {
        return type == TokenType.VARIABLE;
    }

    /**
     * 是否是标量标识
     *
     * @return
     */
    public boolean isScalar() {
        return type == TokenType.BOOLEAN || type == TokenType.FLOAT ||
                type == TokenType.INTEGER || type == TokenType.STRING;
    }

    public static Token makeString(PeekIterator<Character> iterator) throws LexicalException {
        StringBuilder s = new StringBuilder();
        int state = 0;
        while (iterator.hasNext()) {
            char c = iterator.next();
            switch (state) {
                case 0:
                    if (c == '\'') {
                        state = 2;
                    } else {
                        state = 1;
                    }
                    s.append(c);
                    break;
                case 1:
                    if (c == '\"') {
                        return new Token(TokenType.STRING, s.append(c).toString());
                    } else {
                        s.append(c);
                    }
                    break;
                case 2:
                    if (c == '\'') {
                        return new Token(TokenType.STRING, s.append(c).toString());
                    } else {
                        s.append(c);
                    }
                    break;
            }
        }
        throw new LexicalException("unexpected String");
    }

    public static Token makeOp(PeekIterator<Character> it) throws LexicalException {
        int state = 0;

        while(it.hasNext()) {
            var lookahead = it.next();

            switch (state) {
                case 0:
                    switch (lookahead) {
                        case '+':
                            state = 1;
                            break;
                        case '-':
                            state = 2;
                            break;
                        case '*':
                            state = 3;
                            break;
                        case '/':
                            state = 4;
                            break;
                        case '>':
                            state = 5;
                            break;
                        case '<':
                            state = 6;
                            break;
                        case '=':
                            state = 7;
                            break;
                        case '!':
                            state = 8;
                            break;
                        case '&':
                            state = 9;
                            break;
                        case '|':
                            state = 10;
                            break;
                        case '^':
                            state = 11;
                            break;
                        case '%':
                            state = 12;
                            break;
                        case ',':
                            return new Token(TokenType.OPERATOR, ",");
                        case ';':
                            return new Token(TokenType.OPERATOR,  ";");
                    }
                    break;
                case 1:
                    if(lookahead == '+') {
                        return new Token(TokenType.OPERATOR, "++");
                    } else if(lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "+=");
                    }else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "+");
                    }
                case 2:
                    if(lookahead == '-') {
                        return new Token(TokenType.OPERATOR, "--");
                    } else if(lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "-=");
                    }else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "-");
                    }
                case 3:
                    if(lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "*=");
                    }else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "*");
                    }
                case 4:
                    if(lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "/=");
                    }else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "/");
                    }
                case 5:
                    if(lookahead == '=') {
                        return new Token(TokenType.OPERATOR, ">=");
                    }else if(lookahead== '>') {
                        return new Token(TokenType.OPERATOR, ">>");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, ">");

                    }
                case 6:
                    if(lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "<=");
                    }else if(lookahead== '<') {
                        return new Token(TokenType.OPERATOR, "<<");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "<");
                    }
                case 7:
                    if(lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "==");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "=");
                    }
                case 8:
                    if(lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "!=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "!");
                    }
                case 9:
                    if(lookahead == '&') {
                        return new Token(TokenType.OPERATOR, "&&");
                    }  else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "&=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "&");
                    }
                case 10:
                    if(lookahead == '|') {
                        return new Token(TokenType.OPERATOR, "||");
                    }  else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "|=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "|");
                    }
                case 11:
                    if(lookahead == '^') {
                        return new Token(TokenType.OPERATOR, "^^");
                    }  else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "^=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "^");
                    }
                case 12:
                    if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "%=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "%");
                    }
            }

        }
        throw new LexicalException("Unexpected error");
    }
    /**
     * 将字符串流提取成关键字或者变量
     *
     * @param iterator
     * @return
     */
    public static Token makeVarOrKeyWord(PeekIterator<Character> iterator) {
        StringBuilder s = new StringBuilder();
        while (iterator.hasNext()) {
            Character c = iterator.peek();
            if (AlphabetHelper.isLiteral(c)) {
                s.append(c);
            } else {
                break;
            }
            iterator.next();
        }
        if ("true".equals(s.toString()) || "false".equals(s.toString())) {
            return new Token(TokenType.BOOLEAN, s.toString());
        }
        if (KeyWords.isKeyWord(s.toString())) {
            return new Token(TokenType.KEYWORD, s.toString());
        }
        return new Token(TokenType.VARIABLE, s.toString());
    }

    public static Token makeNumber(PeekIterator<Character> iterator){
        StringBuilder s = new StringBuilder();
        int state = 0;
        while (iterator.hasNext()){
            char lookHead = iterator.peek();
            switch (state){
                case 0:
                    
                    break;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("type %s, value %s", type, value);
    }
}
