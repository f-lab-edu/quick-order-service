package com.quickorderservice.exception;

public class DuplicatedIdException extends RuntimeException {

    public DuplicatedIdException() {
        super("아이디가 이미 존재합니다.");
    }

    public DuplicatedIdException(String message) {
        super(message);
    }
}
