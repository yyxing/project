package com.devil.common;

/**
 * @Description
 * @ClassName LexicalException
 * @Author Devil
 * @date 2021.03.15 20:56
 */
public class LexicalException extends Exception {

    private String msg;

    public LexicalException(char c) {
        this.msg = String.format("Unexpected Character %c", c);
    }

    public LexicalException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
