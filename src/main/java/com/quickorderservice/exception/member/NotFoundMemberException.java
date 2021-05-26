package com.quickorderservice.exception.member;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException() {
        super("아이디 혹은 비밀번호가 잘 못 되었습니다.");
    }

    public NotFoundMemberException(String message) {
        super(message);
    }
}
