package com.devil.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * @Description
 * @ClassName PeekIterator
 * @Author Devil
 * @date 2021.03.14 15:38
 */
public class PeekIterator<T> implements Iterator<T> {

    private static final int CACHE_SIZE = 10;
    /**
     * 流迭代器
     */
    private final Iterator<T> it;
    /**
     * 结束字符 类似于EOF
     */
    private T endToken = null;

    /**
     * 指令队列缓存 保留整个流的顺序
     */
    private LinkedList<T> queueCache = new LinkedList<>();
    /**
     * 执行peek操作时 保留上次next取出来的那个值 防止多次调用peek
     */
    private LinkedList<T> stackPutCache = new LinkedList<>();

    public PeekIterator(Stream<T> stream) {
        this.it = stream.iterator();
    }

    public PeekIterator(Stream<T> stream, T endToken) {
        this.it = stream.iterator();
        this.endToken = endToken;
    }

    @Override
    public boolean hasNext() {
        return endToken != null || stackPutCache.size() > 0 || it.hasNext();
    }

    /**
     * 获取流的顶元素 但是不弹出 原理为先从队列中取出来 再加回去
     *
     * @return
     */
    public T peek() {
        // 若未读取任何值时，调用next获取流的第一个值 同时将元素存入缓存队列，此时直接返回都可
        // 若abcdef的流 读取了abc剩余def获取此时的peek也就是d元素
        if (!stackPutCache.isEmpty()) {
            return this.stackPutCache.getFirst();
        }
        if (!this.it.hasNext()) {
            return endToken;
        }
        T val = next();
        this.putBack();
        return val;
    }


    public void putBack() {
        if (queueCache.size() > 0) {
            stackPutCache.push(queueCache.pollLast());
        }
    }

    /**
     * a -> b -> c
     * b -> c
     * 缓存 a
     *
     * @return
     */
    @Override
    public T next() {
        T val = null;

        // 若栈中有值 说明执行了peek获取到了next元素再使用next时 直接返回即可
        if (stackPutCache.size() > 0) {
            val = this.stackPutCache.pop();
        } else {
            if (!this.it.hasNext()) {
                T tmp = endToken;
                endToken = null;
                return tmp;
            }
            val = it.next();
        }
        // 将指令加到缓存中
        if (queueCache.size() > CACHE_SIZE - 1) {
            queueCache.poll();
        }
        queueCache.add(val);
        return val;
    }
}
