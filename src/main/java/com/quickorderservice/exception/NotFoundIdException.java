package com.quickorderservice.exception;

public class NotFoundIdException extends RuntimeException {

    public NotFoundIdException() {
        super("아이디가 존재하지 않습니다.");
    }

    public NotFoundIdException(String message) {
        super(message);
    }
}
