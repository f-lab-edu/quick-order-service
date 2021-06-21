package com.quickorderservice.exception.auth;

public class NeedLoginException extends RuntimeException {

    public NeedLoginException() {
        super("로그인이 필요합니다.");
    }

    public NeedLoginException(String message) {
        super(message);
    }
}
