package com.github.microwww.redis.exception;

/**
 * 当对持有错误类型值的 key 执行操作时抛出此异常。
 * 例如：对一个 SortedSet 类型的 key 执行 GET 命令。
 * 
 * Redis 标准错误消息：
 * WRONGTYPE Operation against a key holding the wrong kind of value
 */
public class WrongTypeException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public static final String MESSAGE = "WRONGTYPE Operation against a key holding the wrong kind of value";
    
    public WrongTypeException() {
        super(MESSAGE);
    }
    
    public WrongTypeException(String message) {
        super(message);
    }
}
