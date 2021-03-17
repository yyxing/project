package com.devil.common;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @Description
 * @ClassName PeekIterator
 * @Author Devil
 * @date 2021.03.14 18:27
 */
public class PeekIteratorTest {

    @Test
    public void test_peek(){
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<>(source.chars().mapToObj(c -> (char)c));
        assertEquals('a',it.next());
        assertEquals('b',it.next());
        assertEquals('c',it.next());
        assertEquals('d',it.next());
        assertEquals('e',it.next());
        assertEquals('f',it.peek());
        assertEquals('f',it.peek());
        assertEquals('f',it.next());
        assertEquals('g',it.peek());
        assertEquals('g',it.next());
    }

    @Test
    public void test_pushback(){
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<>(source.chars().mapToObj(c -> (char)c));
        assertEquals('a',it.next());
        assertEquals('b',it.next());
        assertEquals('c',it.next());
        it.putBack();
        it.putBack();
        assertEquals('b',it.next());
        assertEquals('c',it.next());
    }

    @Test
    public void test_endToken(){
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<>(source.chars().mapToObj(c -> (char)c),'0');
        assertEquals('a',it.next());
        assertEquals('b',it.next());
        assertEquals('c',it.next());
        assertEquals('d',it.next());
        assertEquals('e',it.next());
        assertEquals('f',it.next());
        assertEquals('g',it.next());
        assertEquals('0',it.next());
    }
}
