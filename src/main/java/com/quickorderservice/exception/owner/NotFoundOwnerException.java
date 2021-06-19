package com.quickorderservice.exception.owner;

public class NotFoundOwnerException extends RuntimeException {

    public NotFoundOwnerException() {
        super("아이디 혹은 비밀번호가 잘 못 되었습니다.");
    }

    public NotFoundOwnerException(String message) {
        super(message);
    }
}
